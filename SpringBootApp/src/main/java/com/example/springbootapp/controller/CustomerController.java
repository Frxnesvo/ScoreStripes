package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.AddressService;
import com.example.springbootapp.service.interfaces.CustomerService;
import com.example.springbootapp.service.interfaces.OrderService;
import com.example.springbootapp.utils.ExceptionUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins ="*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final ExceptionUtils exceptionUtils;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/summary")
    public ResponseEntity<Page<CustomerSummaryDto>> getCustomersSummary(Pageable pageable, @RequestParam(required = false) String username){
        return ResponseEntity.ok(customerService.getCustomersSummary(username,pageable));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/{id}/profile")  //da mettere in sicurezza in modo tale che qui può accedere solo l'utente loggato o un admin
    public ResponseEntity<CustomerProfileDto> getCustomerProfile(@PathVariable String id){
        CustomerProfileDto c= customerService.getCustomerProfile(id);
        return ResponseEntity.ok(c);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/{id}/addresses")    //da mettere in sicurezza in modo tale che qui può accedere solo l'utente loggato o un admin
    public ResponseEntity<List<AddressDto>> getCustomerAddresses(@PathVariable String id){
        return ResponseEntity.ok(addressService.getAddressesByCustomerId(id));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/{id}/orders")    //da mettere in sicurezza in modo tale che qui può accedere solo l'utente loggato o un admin
    public ResponseEntity<List<OrderDto>> getFullCustomerOrders(@PathVariable String id){
        return ResponseEntity.ok(orderService.getFullOrdersByCustomerId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("new-accounts") // conta il numero di custoemer account creati nelle ultime 24 ore
    public ResponseEntity<Long> countNewAccounts(){
        return ResponseEntity.ok(customerService.countNewAccounts());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/address")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressRequestDto addressRequestDto) {
        return ResponseEntity.ok(addressService.addAddress(addressRequestDto));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Map<String, String>> deleteAddress(@PathVariable String id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok(Map.of("response", "Address deleted"));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @PatchMapping(path="/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String,String>> updateCustomer(@PathVariable String id, @Valid @ModelAttribute CustomerUpdateDto customerUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
        }
        customerService.updateCustomer(id, customerUpdateDto);
        return ResponseEntity.ok(Map.of("response", "Customer updated"));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping(path="/update-customer", consumes = {"multipart/form-data"})  //Endpoint uguale a quello sopra ma senza passare l'id (richiesta del front-end), quello sopra lo lascio uguale perchè è utlizzabile anche da admin e più modulare
    public ResponseEntity<Map<String,String>> updateCustomer2(@Valid @ModelAttribute CustomerUpdateDto customerUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
        }
        customerService.updateCustomer2(customerUpdateDto);
        return ResponseEntity.ok(Map.of("response", "Customer updated"));
    }
}
