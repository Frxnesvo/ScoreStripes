package com.example.springbootapp.reactiveProgramming;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "orders")
@Data
@RequiredArgsConstructor
public class OrderReactive {
    @Id
    private String id;

    private Double totalPrice;

    private String date;

    private String status;

}
