package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AddToCartRequestDto;

public interface CartService {
    void addProductToCart(AddToCartRequestDto requestDto);
}
