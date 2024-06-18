package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.AddressDao;
import com.example.springbootapp.data.dao.CartDao;
import com.example.springbootapp.data.dto.EmailInfosDto;
import com.example.springbootapp.data.dto.OrderDto;
import com.example.springbootapp.data.dao.OrderDao;
import com.example.springbootapp.data.dto.OrderInfosRequestDto;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Embdeddables.OrderInformations;
import com.example.springbootapp.data.entities.Enums.OrderStatus;
import com.example.springbootapp.exceptions.EmailMessagingException;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.exceptions.StripeSessionException;
import com.example.springbootapp.handler.PaymentHandler;
import com.example.springbootapp.service.interfaces.EmailService;
import com.example.springbootapp.service.interfaces.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
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
    private final AddressDao addressDao;
    private final CartDao cartDao;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final PaymentHandler paymentHandler;
    private final EmailService emailService;


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
        List<Address> addresses = addressDao.findByCustomerId(loggedCustomer.getId());
        Address address = addresses.stream()
                .filter(a -> a.getId().equals(orderInfos.getAddressId()))
                .findFirst()
                .orElseThrow(() -> new RequestValidationException("Address not found"));
        Cart cart = cartDao.findById(loggedCustomer.getCart().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        if(cart.getCartItems().isEmpty()) {
            throw new RequestValidationException("Cart is empty");
        }
        OrderInformations resilientInfos = modelMapper.map(address, OrderInformations.class);
        resilientInfos.setCustomerEmail(loggedCustomer.getEmail());
        resilientInfos.setCustomerFirstName(loggedCustomer.getFirstName());
        resilientInfos.setCustomerLastName(loggedCustomer.getLastName());
        Order order = new Order();
        order.setResilientInfos(resilientInfos);
        order.setCustomer(loggedCustomer);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order= orderDao.save(order);  //salvo l'ordine per avere l'id
        Order finalOrder = order;  //finalOrder è usata solo per la lambda (non può essere modificata)
        List<OrderItem> items= cart.getCartItems().stream()
                .map(cartItem -> modelMapper.map(cartItem, OrderItem.class))
                .peek(orderItem -> orderItem.setOrder(finalOrder))
                .collect(Collectors.toList());
        order.setItems(items);
        order= orderDao.save(order);  //salvo l'ordine con gli item
        try{
            return paymentHandler.startCheckoutProcess(order);
        }
        catch (Exception e) {
            throw new StripeSessionException(e.getMessage());
        }
    }

    public String validateOrder (String sessionId) {        //TODO: forse voglio ritornare un dto
        try {
            String orderId = paymentHandler.getOrderIdFromSession(sessionId);
            Order order = orderDao.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
            if (paymentHandler.checkTransactionStatus(sessionId)) {
                order.setStatus(OrderStatus.COMPLETED);
                orderDao.save(order);
                EmailInfosDto emailInfos = new EmailInfosDto();          //TODO: maybe potrei usare il modelMapper per fare il mapping
                emailInfos.setTo(order.getResilientInfos().getCustomerEmail());
                emailInfos.setName(order.getResilientInfos().getCustomerFirstName());
                emailInfos.setOrderId(order.getId());
                emailInfos.setTotalCost(order.getTotalPrice());
                emailInfos.setOrderDate(order.getDate().toString());
                emailService.sendOrderConfirmationEmail(emailInfos);
                return "Order paid";
            }
            else {
                order.setStatus(OrderStatus.CANCELLED);
                orderDao.save(order);
                return "Order cancelled";
            }
        } catch (StripeException e) {
            throw new StripeSessionException(e.getMessage());
        } catch (MessagingException e) {
            throw new EmailMessagingException("Error sending email");
        }
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
