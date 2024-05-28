package com.example.springbootapp.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailInfosDto {
    String to;
    String name;
    String orderId;
    double totalCost;
    String orderDate;
}
