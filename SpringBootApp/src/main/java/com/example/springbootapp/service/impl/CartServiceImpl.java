package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.CartDao;
import com.example.springbootapp.data.dao.CartItemDao;
import com.example.springbootapp.data.dao.CustomerDao;
import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.data.dto.CartItemDto;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.service.interfaces.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductDao productDao;
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    //FIXME: Ho centralizzato sia la logica per aggiungere sia per aumentare la quantità di un prodotto nel carrello. Dovrei fare 2 metodi separati?
    //TODO: L'endpoint per aggiungere un prodotto al carrello dovrebbe essere accessibile solo ai customer non agli admin.

    @Override
    public CartItemDto addProductToCart(AddToCartRequestDto requestDto) {
        Product product = productDao.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        ProductWithVariant variant = product.getVariants().stream().filter(p -> p.getSize()
                .equals(requestDto.getSize())).findFirst().orElseThrow(() -> new EntityNotFoundException("Variant not found"));
        Cart cart = ((Customer) userDetailsService.getCurrentUser()).getCart();
        CartItem newItem = new CartItem();
        newItem.setProduct(variant);
        newItem.setQuantity(1);
        newItem.setPersonalization(requestDto.getPersonalization());
        newItem.setCart(cart);
        if(cart.getCartItems().contains(newItem)){
            cart.getCartItems().stream().filter(i -> i.equals(newItem)).findFirst().ifPresent(item -> item.setQuantity(item.getQuantity() + 1));
        }else{
            cart.getCartItems().add(newItem);
        }
        cartDao.save(cart);
        return modelMapper.map(newItem, CartItemDto.class);
    }

    @Override
    public List<CartItemDto> getMyCart() {
        List<CartItem> cartItems = cartItemDao.findAllByCartId(((Customer) userDetailsService.getCurrentUser()).getCart().getId());
        return cartItems.stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .toList();
    }
}
