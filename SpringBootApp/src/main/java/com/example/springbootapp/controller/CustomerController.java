package com.example.springbootapp.controller;

import com.example.springbootapp.Data.DTO.AddressDto;
import com.example.springbootapp.Data.DTO.CustomerProfileDto;
import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import com.example.springbootapp.Data.DTO.OrderDto;
import com.example.springbootapp.service.interfaces.AddressService;
import com.example.springbootapp.service.interfaces.CustomerService;
import com.example.springbootapp.service.interfaces.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<CustomerSummaryDto>> getCustomerSummaries(Pageable pageable){
        return ResponseEntity.ok(customerService.getCustomersSummary(pageable));
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

}
