package com.example.springbootapp.Data.Entities.Embdeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OrderInformations {
    private String street;
    private String city;
    private String state;
    private String zip_code;
    private String civic_number;
    private String customer_email;
    private String customer_firstName;
    private String customer_lastName;
}
