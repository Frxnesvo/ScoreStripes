package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.CustomerProfileDto;
import com.example.springbootapp.data.dto.CustomerSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    public Page<CustomerSummaryDto> getCustomersSummary(Pageable pageable);
    public CustomerProfileDto getCustomerProfile(String id);

    Long countNewAccounts();
}
