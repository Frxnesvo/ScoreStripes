package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.service.interfaces.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProductToCart(@Valid @RequestBody AddToCartRequestDto requestDto) {
        cartService.addProductToCart(requestDto);
        return ResponseEntity.ok("Product added to cart");
    }
}
