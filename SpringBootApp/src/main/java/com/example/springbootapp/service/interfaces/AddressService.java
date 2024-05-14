package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddressesByCustomerId(String customerId);
}
