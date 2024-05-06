package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.OrderDto;
import java.util.List;

public interface OrderService {
    List<OrderDto> getFullOrdersByCustomerId(String customerId);
}
