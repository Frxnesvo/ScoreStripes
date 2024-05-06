package com.example.springbootapp.Data.DTO;

import com.example.springbootapp.Data.Entities.Embdeddables.OrderInformations;
import com.example.springbootapp.Data.Entities.Enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private String id;
    private Double totalPrice;
    private LocalDate date;
    private OrderStatus status;
    private OrderInformations resilientInfos;
    private List<OrderItemDto> items;
}
