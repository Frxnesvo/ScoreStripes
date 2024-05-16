package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.AddressDto;
import com.example.springbootapp.data.dto.CustomerProfileDto;
import com.example.springbootapp.data.dto.CustomerSummaryDto;
import com.example.springbootapp.data.dto.OrderDto;
import com.example.springbootapp.data.entities.Customer;
import com.example.springbootapp.data.specification.CustomerSpecification;
import com.example.springbootapp.service.interfaces.AddressService;
import com.example.springbootapp.service.interfaces.CustomerService;
import com.example.springbootapp.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins ="*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderService orderService;

    @GetMapping("/summary")
    public ResponseEntity<Page<CustomerSummaryDto>> getCustomersSummary(Pageable pageable, @RequestParam(required = false) String username){
        return ResponseEntity.ok(customerService.getCustomersSummary(username,pageable));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<CustomerProfileDto> getCustomerProfile(@PathVariable String id){
        CustomerProfileDto c= customerService.getCustomerProfile(id);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<AddressDto>> getCustomerAddresses(@PathVariable String id){
        return ResponseEntity.ok(addressService.getAddressesByCustomerId(id));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getFullCustomerOrders(@PathVariable String id){
        return ResponseEntity.ok(orderService.getFullOrdersByCustomerId(id));
    }

    @GetMapping("new-accounts") // conta il numero di custoemer account creati nelle ultime 24 ore
    public ResponseEntity<Long> countNewAccounts(){
        return ResponseEntity.ok(customerService.countNewAccounts());
    }

}
