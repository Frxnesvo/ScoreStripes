package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    public Page<CustomerSummaryDto> getCustomersSummary(Pageable pageable);
}
