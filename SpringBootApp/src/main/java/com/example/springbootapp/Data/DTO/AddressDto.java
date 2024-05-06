package com.example.springbootapp.Data.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    private String id;
    private String street;
    private String city;
    private String civicNumber;
    private String zipCode;
    private String state;
    private Boolean defaultAddress;
}
