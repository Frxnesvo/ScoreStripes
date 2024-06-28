package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Embdeddables.OrderInformations;
import com.example.springbootapp.data.entities.Enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private String id;
    private Double totalPrice;
    private LocalDateTime date;
    private OrderStatus status;
    private OrderInformations resilientInfos;
    private List<OrderItemDto> items;
}
