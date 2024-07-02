package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AddressDto;
import com.example.springbootapp.data.dto.AddressRequestDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddressesByCustomerId(String customerId);
    AddressDto addAddress(AddressRequestDto addressRequestDto);

    void deleteAddress(String id);
}
