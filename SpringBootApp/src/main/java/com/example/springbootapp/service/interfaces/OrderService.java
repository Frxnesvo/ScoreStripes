package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.OrderDto;
import java.util.List;

public interface OrderService {
    List<OrderDto> getFullOrdersByCustomerId(String customerId);

    Long countNewOrders();

}
