package com.example.springbootapp.Data.Entities.Embdeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AddressEmbeddable {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String civicNumber;
}
