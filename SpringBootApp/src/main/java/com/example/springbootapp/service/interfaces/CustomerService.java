package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.CustomerProfileDto;
import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    public Page<CustomerSummaryDto> getCustomersSummary(Pageable pageable);
    public CustomerProfileDto getCustomerProfile(String id);

    Long countNewAccounts();
}
