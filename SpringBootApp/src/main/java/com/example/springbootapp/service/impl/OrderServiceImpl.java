package com.example.springbootapp.service.impl;

import com.example.springbootapp.Data.DTO.OrderDto;
import com.example.springbootapp.Data.Dao.OrderDao;
import com.example.springbootapp.Data.Entities.Order;
import com.example.springbootapp.service.interfaces.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public List<OrderDto> getFullOrdersByCustomerId(String customerId) {
        List<Order> orders = orderDao.findByCustomerId(customerId);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private OrderDto convertToDto(Order order) {
        //initialize the lazy loading fields
        Hibernate.initialize(order.getItems());
        order.getItems().forEach(item -> {
            Hibernate.initialize(item.getProduct().getProduct().getClub());
            Hibernate.initialize(item.getProduct().getProduct().getPics());
        });

        return modelMapper.map(order, OrderDto.class);
    }
}
