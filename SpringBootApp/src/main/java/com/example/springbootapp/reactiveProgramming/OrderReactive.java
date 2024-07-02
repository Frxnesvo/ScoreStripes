package com.example.springbootapp.reactiveProgramming;

import com.example.springbootapp.data.entities.Enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "orders")
@Data
@RequiredArgsConstructor
public class OrderReactive {
    @Id
    private String id;

    private Double totalPrice;

    private String date;

    //@Enumerated(EnumType.STRING)
    //private OrderStatus status;

    private String status;

}
