package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.AddressDto;
import com.example.springbootapp.Data.Entities.Address;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddressesByCustomerId(String customerId);
}
