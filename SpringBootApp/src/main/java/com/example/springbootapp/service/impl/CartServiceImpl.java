package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.CartDao;
import com.example.springbootapp.data.dao.CartItemDao;
import com.example.springbootapp.data.dao.CustomerDao;
import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.data.dto.CartItemDto;
import com.example.springbootapp.data.dto.CartItemUpdateDto;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.service.interfaces.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        System.out.println("personalizzazione "+requestDto.getPersonalization().toString());
        Product product = productDao.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        ProductWithVariant variant = product.getVariants().stream().filter(p -> p.getSize()
                .equals(requestDto.getSize())).findFirst().orElseThrow(() -> new EntityNotFoundException("Variant not found"));
        if(variant.getAvailability() <= 0){
            throw new RequestValidationException("Product out of stock"); //TODO: cambiare eccezione
        }
        Optional<Cart> cart = cartDao.findById(((Customer) userDetailsService.getCurrentUser()).getCart().getId());
        if(cart.isEmpty()){
            throw new RequestValidationException("Cart not found"); //impossibile
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(variant);
        newItem.setQuantity(1);
        newItem.setPersonalization(requestDto.getPersonalization());
        newItem.setCart(cart.get());
        Optional<CartItem> existingItem = cart.get().getCartItems().stream()
                .filter(newItem::equals)
                .findFirst();
        if(existingItem.isPresent()){
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
            if(variant.getAvailability() < existingItem.get().getQuantity()){
                throw new RequestValidationException("Product out of stock");  //TODO: cambiare eccezione
            }
            newItem=cartItemDao.save(existingItem.get());
        }
        else {
            newItem = cartItemDao.save(newItem);
        }
        return modelMapper.map(newItem, CartItemDto.class);
    }

    @Override
    public List<CartItemDto> getMyCart() {
        List<CartItem> cartItems = cartItemDao.findAllByCartId(((Customer) userDetailsService.getCurrentUser()).getCart().getId());
        return cartItems.stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .toList();
    }

    @Override
    public void deleteItemFromCart(String itemId) {
        Customer customer = (Customer) userDetailsService.getCurrentUser();
        Cart cart= cartDao.findById(customer.getCart().getId()).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        List<CartItem> items = cartItemDao.findAllByCartId(cart.getId());  //Non riesco a non fare la query. cart.getCartItems() non va in nessun modo per colpa della sessione (ho provato qualsiasi cosa)
        CartItem item = items.stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RequestValidationException("Item not found in cart"));
        cartItemDao.delete(item);
    }

    @Override
    public void updateItemQuantity(String itemId, CartItemUpdateDto updateDto) {
        Customer customer = (Customer) userDetailsService.getCurrentUser();
        Cart cart= cartDao.findById(customer.getCart().getId()).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RequestValidationException("Item not found in cart"));
        if(item.getProduct().getAvailability() < updateDto.getQuantity()){
            throw new RequestValidationException("Product stock not sufficient");  //TODO: cambiare eccezione
        }
        item.setQuantity(updateDto.getQuantity());
        cartDao.save(customer.getCart());
    }
}
