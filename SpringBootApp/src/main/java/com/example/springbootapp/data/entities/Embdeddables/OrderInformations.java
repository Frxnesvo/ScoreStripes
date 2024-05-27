package com.example.springbootapp.data.entities.Embdeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OrderInformations {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String civicNumber;
    private String customerEmail;
    private String customerFirstName;
    private String customerLastName;
}
