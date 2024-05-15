package com.example.springbootapp.controller;

import com.example.springbootapp.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/new-orders") // conta il numero di ordini creati nelle ultime 24 ore
    public ResponseEntity<Long> countNewOrders(){
        return ResponseEntity.ok(orderService.countNewOrders());
    }
}
