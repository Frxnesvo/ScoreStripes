package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.data.dto.CartItemDto;
import com.example.springbootapp.data.dto.CartItemUpdateDto;

import java.util.List;

public interface CartService {
    CartItemDto addProductToCart(AddToCartRequestDto requestDto);

    List<CartItemDto> getMyCart();

    void deleteItemFromCart(String itemId);

    void updateItemQuantity(String itemId, CartItemUpdateDto updateDto);
}
