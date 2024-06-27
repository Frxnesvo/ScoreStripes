package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.*;
import com.example.springbootapp.data.dto.EmailInfosDto;
import com.example.springbootapp.data.dto.OrderDto;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
    private final ProductWithVariantDao productWithVariantDao;


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
        cart.getCartItems().forEach(cartItem -> {
            if(cartItem.getProduct().getAvailability() < cartItem.getQuantity()) {
                throw new RequestValidationException("One or more products are out of stock"); //TODO: cambiare eccezione
            }
        });
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
        cart.getCartItems().clear();  //elimino tutti gli item dal carrello
        cartDao.save(cart);
        try{
            return paymentHandler.startCheckoutProcess(order);
        }
        catch (Exception e) {
            throw new StripeSessionException(e.getMessage());
        }
    }

    @Override
    public String validateOrder (String sessionId) {        //TODO: forse voglio ritornare un dto
        try {
            if(sessionId == null) {
                throw new RequestValidationException("SessionId is null");
            }
            String orderId = paymentHandler.getOrderIdFromSession(sessionId);
            Order order = orderDao.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
            order.getItems().forEach(orderItem -> {
                if(orderItem.getProduct().getAvailability() < orderItem.getQuantity()) {
                    order.setStatus(OrderStatus.CANCELLED);
                    orderDao.save(order);
                    throw new RequestValidationException("One or more products are out of stock"); //TODO: andrebbe gestito un possibile rimborso
                }
            });
            if (paymentHandler.checkTransactionStatus(sessionId)) {
                order.setStatus(OrderStatus.COMPLETED);
                order.getItems().forEach(orderItem -> {
                    ProductWithVariant product = orderItem.getProduct();
                    product.setAvailability(product.getAvailability() - orderItem.getQuantity());
                    productWithVariantDao.save(product);
                });
                orderDao.save(order);
                sendOrderConfirmationEmailAsync(order);
                return OrderStatus.COMPLETED.name();
            }
            else {
                order.setStatus(OrderStatus.CANCELLED);
                orderDao.save(order);
                return OrderStatus.CANCELLED.name();
            }
        } catch (StripeException e) {
            throw new StripeSessionException(e.getMessage());
        }
    }

    private OrderDto convertToDto(Order order) {
        Hibernate.initialize(order.getItems());
        order.getItems().forEach(item -> {
            Hibernate.initialize(item.getProduct().getProduct().getClub());
            Hibernate.initialize(item.getProduct().getProduct().getPics());
        });
        return modelMapper.map(order, OrderDto.class);
    }

    @Async
    protected CompletableFuture<Void> sendOrderConfirmationEmailAsync(Order order) {
        EmailInfosDto emailInfos = new EmailInfosDto();
        emailInfos.setTo(order.getResilientInfos().getCustomerEmail());
        emailInfos.setName(order.getResilientInfos().getCustomerFirstName());
        emailInfos.setOrderId(order.getId());
        emailInfos.setTotalCost(order.getTotalPrice());
        emailInfos.setOrderDate(order.getDate().toString());
        try {
            emailService.sendOrderConfirmationEmail(emailInfos);
        } catch (MessagingException e) {
            System.out.println("Error sending email");
            throw new EmailMessagingException("Error sending email");
        }
        return CompletableFuture.completedFuture(null);
    }
}
