package com.example.springbootapp.service.impl;

import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import com.example.springbootapp.Data.Dao.CustomerDao;
import com.example.springbootapp.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerDao customerDao;

    @Override
    public Page<CustomerSummaryDto> getCustomersSummary(Pageable pageable) {
        return customerDao.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerSummaryDto.class));
    }
}
