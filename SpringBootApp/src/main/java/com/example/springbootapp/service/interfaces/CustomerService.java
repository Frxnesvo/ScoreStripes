package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.CustomerProfileDto;
import com.example.springbootapp.data.dto.CustomerSummaryDto;
import com.example.springbootapp.data.dto.CustomerUpdateDto;
import com.example.springbootapp.data.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CustomerService {
    Page<CustomerSummaryDto> getCustomersSummary(String username, Pageable pageable);

    CustomerProfileDto getCustomerProfile(String id);

    Long countNewAccounts();

    void updateCustomer(String id, CustomerUpdateDto customerUpdateDto);
}
