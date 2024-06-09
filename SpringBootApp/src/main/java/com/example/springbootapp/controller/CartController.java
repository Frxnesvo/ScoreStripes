package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.data.dto.CartItemDto;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/carts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-product")    //TODO: proteggere endpoint dagli admin, cambiare path in /item/add
    public ResponseEntity<CartItemDto> addProductToCart(@Valid @RequestBody AddToCartRequestDto requestDto) {
        return ResponseEntity.ok(cartService.addProductToCart(requestDto));
    }

    @GetMapping("/my-cart")
    public ResponseEntity<List<CartItemDto>> getMyCart(){
        return ResponseEntity.ok(cartService.getMyCart());
    }


    //@PatchMapping("/item/{itemId}")

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<String> deleteItemFromCart(@PathVariable String itemId){
        cartService.deleteItemFromCart(itemId);
        return ResponseEntity.ok("Item deleted");
    }

}
