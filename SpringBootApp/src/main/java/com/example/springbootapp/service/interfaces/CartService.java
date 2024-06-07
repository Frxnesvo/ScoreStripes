package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.data.dto.CartItemDto;

import java.util.List;

public interface CartService {
    void addProductToCart(AddToCartRequestDto requestDto);

    List<CartItemDto> getMyCart();
}
