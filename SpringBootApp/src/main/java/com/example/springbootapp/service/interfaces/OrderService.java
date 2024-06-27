package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.OrderDto;
import com.example.springbootapp.data.dto.OrderInfosRequestDto;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderDto> getFullOrdersByCustomerId(String customerId);

    Long countNewOrders();

    String createOrderFromCart(OrderInfosRequestDto orderInfos);

    //String validateOrder(String sessionId);

    Map<String, String> validateOrder(String sessionId);
}
