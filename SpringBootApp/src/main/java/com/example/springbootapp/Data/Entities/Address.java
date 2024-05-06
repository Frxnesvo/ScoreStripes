package com.example.springbootapp.Data.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
public class Address {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "civic_number", nullable = false)
    private String civicNumber;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "default_address")
    private Boolean defaultAddress;

    @ManyToOne(fetch = FetchType.LAZY)  //lazy perchè tanto non avrò bisogno del customer mai
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
