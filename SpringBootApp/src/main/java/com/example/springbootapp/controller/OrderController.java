package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.OrderInfosRequestDto;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new-orders") // conta il numero di ordini creati nelle ultime 24 ore
    public ResponseEntity<Long> countNewOrders(){
        return ResponseEntity.ok(orderService.countNewOrders());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create-cart-order")
        public ResponseEntity<Map<String,String>> createOrder(@RequestBody OrderInfosRequestDto orderInfos){
            return ResponseEntity.ok(Map.of("url", orderService.createOrderFromCart(orderInfos)));
    }

    @PostMapping("/validate-transaction")
    public ResponseEntity<Map<String,String>> validateTransaction(@RequestParam String sessionId) {
        return ResponseEntity.ok(orderService.validateOrder(sessionId));
    }


}
