package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.CustomerProfileDto;
import com.example.springbootapp.data.dto.CustomerSummaryDto;
import com.example.springbootapp.data.dao.CustomerDao;
import com.example.springbootapp.service.interfaces.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerDao customerDao;

    @Override
    public Page<CustomerSummaryDto> getCustomersSummary(Pageable pageable) {
        if(pageable.getSort().isUnsorted()) {
            Sort sort = Sort.by(Sort.Direction.ASC, "username");
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return customerDao.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerSummaryDto.class));
    }

    @Override
    public CustomerProfileDto getCustomerProfile(String id) {
        return modelMapper.map(customerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found")), CustomerProfileDto.class);
    }

    @Override
    public Long countNewAccounts() {
        return customerDao.countAllByCreatedDateAfter(LocalDateTime.now().minusDays(1));
    }
}
