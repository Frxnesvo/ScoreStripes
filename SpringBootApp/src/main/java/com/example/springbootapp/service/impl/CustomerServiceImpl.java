package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.CustomerProfileDto;
import com.example.springbootapp.data.dto.CustomerSummaryDto;
import com.example.springbootapp.data.dao.CustomerDao;
import com.example.springbootapp.data.dto.CustomerUpdateDto;
import com.example.springbootapp.data.entities.Customer;
import com.example.springbootapp.data.specification.CustomerSpecification;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerDao customerDao;
    private final AwsS3Service awsS3Service;


    @Override
    public Page<CustomerSummaryDto> getCustomersSummary(String username, Pageable pageable) {
        if(pageable.getSort().isUnsorted()) {
            Sort sort = Sort.by(Sort.Direction.ASC, "username");
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        Specification<Customer> spec = Specification.where(null);
        if(username != null) {
            spec = spec.and(CustomerSpecification.usernameContains(username));
        }
        return customerDao.findAll(spec,pageable).map(customer -> modelMapper.map(customer, CustomerSummaryDto.class));
    }

    @Override
    public CustomerProfileDto getCustomerProfile(String id) {
        return modelMapper.map(customerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found")), CustomerProfileDto.class);
    }

    @Override
    public Long countNewAccounts() {
        return customerDao.countAllByCreatedDateAfter(LocalDateTime.now().minusDays(1));
    }

    @Override
    public void updateCustomer(String id, CustomerUpdateDto customerUpdateDto) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if(customerUpdateDto.getFavoriteTeam() != null)
            customer.setFavouriteTeam(customerUpdateDto.getFavoriteTeam());
        if(customerUpdateDto.getProfilePic() != null){
            String profilePicUrl = awsS3Service.uploadFile(customerUpdateDto.getProfilePic(),"users", customer.getUsername());
            customer.setProfilePicUrl(profilePicUrl);
        }
        customerDao.save(customer);
    }
}
