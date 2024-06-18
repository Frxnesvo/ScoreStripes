package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.CustomerDao;
import com.example.springbootapp.data.dto.AddressDto;
import com.example.springbootapp.data.dao.AddressDao;
import com.example.springbootapp.data.dto.AddressRequestDto;
import com.example.springbootapp.data.entities.Address;
import com.example.springbootapp.data.entities.Customer;
import com.example.springbootapp.data.entities.User;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomerDao customerDao;

    @Override
    public List<AddressDto> getAddressesByCustomerId(String customerId){
        return addressDao.findByCustomerId(customerId).stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public AddressDto addAddress(AddressRequestDto addressRequestDto) {
        Customer loggedCustomer =(Customer) userDetailsService.getCurrentUser();
        if(addressRequestDto.getDefaultAddress()){
            List<Address> addresses = addressDao.findByCustomerId(loggedCustomer.getId());
            addresses.stream()
                    .filter(Address::getDefaultAddress)
                    .forEach(address -> {
                        address.setDefaultAddress(false);
                        addressDao.save(address);
                    });
        }
        Address address = modelMapper.map(addressRequestDto, Address.class);  //DI SOLITO NON USO IL MAPPER DA DTO A ENTITY MA IN QUESTO SONO PRATICAMENTE IDENTICI
        address.setCustomer(loggedCustomer);
        return modelMapper.map(addressDao.save(address), AddressDto.class);
    }
}
