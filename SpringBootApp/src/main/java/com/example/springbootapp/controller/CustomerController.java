package com.example.springbootapp.controller;

import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import com.example.springbootapp.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins ="*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/summary")
    public ResponseEntity<Page<CustomerSummaryDto>> getCustomerSummaries(Pageable pageable){
        return ResponseEntity.ok(customerService.getCustomersSummary(pageable));
    }


}
