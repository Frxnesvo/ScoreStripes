package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.OrderDto;
import com.example.springbootapp.data.dao.OrderDao;
import com.example.springbootapp.data.dto.OrderInfosRequestDto;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Embdeddables.OrderInformations;
import com.example.springbootapp.data.entities.Enums.OrderStatus;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.handler.PaymentHandler;
import com.example.springbootapp.service.interfaces.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final PaymentHandler paymentHandler;


    @Override
    @Transactional
    public List<OrderDto> getFullOrdersByCustomerId(String customerId) {
        List<Order> orders = orderDao.findByCustomerId(customerId);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long countNewOrders() {
        return orderDao.countByDateAfter(LocalDateTime.now().minusDays(1));
    }

    @Override
    @Transactional
    public String createOrderFromCart(OrderInfosRequestDto orderInfos) {
        Customer loggedCustomer = (Customer) userDetailsService.getCurrentUser();
        List<Address> addresses = loggedCustomer.getAddresses();
        Address address = addresses.stream()
                .filter(a -> a.getId().equals(orderInfos.getAddressId()))
                .findFirst()
                .orElseThrow(() -> new RequestValidationException("Address not found"));
        OrderInformations resilientInfos = modelMapper.map(address, OrderInformations.class);
        resilientInfos.setCustomerEmail(loggedCustomer.getEmail());
        resilientInfos.setCustomerFirstName(loggedCustomer.getFirstName());
        resilientInfos.setCustomerLastName(loggedCustomer.getLastName());

        Cart cart = loggedCustomer.getCart();
        Order order = new Order();
        order.setResilientInfos(resilientInfos);
        order.setCustomer(loggedCustomer);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        List<OrderItem> items= cart.getCartItems().stream()
                .map(cartItem -> modelMapper.map(cartItem, OrderItem.class))
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toList());
        order.setItems(items);
        orderDao.save(order);

        //TODO: gestire il pagamento


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
