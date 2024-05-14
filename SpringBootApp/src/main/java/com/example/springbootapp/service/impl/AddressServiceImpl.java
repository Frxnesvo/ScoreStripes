package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.AddressDto;
import com.example.springbootapp.data.dao.AddressDao;
import com.example.springbootapp.service.interfaces.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;
    private final ModelMapper modelMapper;
    @Override
    public List<AddressDto> getAddressesByCustomerId(String customerId){
        return addressDao.findByCustomerId(customerId).stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }
}
