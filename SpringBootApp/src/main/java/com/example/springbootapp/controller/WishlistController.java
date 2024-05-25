package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.WishlistService;
import com.example.springbootapp.service.interfaces.WishlistShareTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/wishlists")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final WishlistShareTokenService wishlistShareTokenService;

    @PatchMapping("/visibility")
    public ResponseEntity<String> changeVisibility(@Valid @RequestBody WishlistVisibilityDto visibilityDto){
        wishlistService.changeVisibility(visibilityDto);
        return ResponseEntity.ok("Visibility changed");
    }

    @PostMapping("/createShareToken")
    public ResponseEntity<WishlistShareTokenDto> createShareToken(){
        return ResponseEntity.ok(wishlistShareTokenService.createShareToken());
    }

    @PostMapping("/validateShareAccess")
    public ResponseEntity<String> validateShareAccess(@RequestBody String token){  //TODO: Ha senso inglobare il token in un Dto e validarlo per evitare query?
        return ResponseEntity.ok(wishlistShareTokenService.validateShareAccess(token));
    }

    @PostMapping("/add-Item")
    public ResponseEntity<String> addItemToWishlist(@Valid @RequestBody AddToWishlistRequestDto requestDto){
        wishlistService.addItemToWishlist(requestDto);
        return ResponseEntity.ok("Item added to wishlist");
    }

    @GetMapping("/shared-with-me")
    public ResponseEntity<List<WishlistDto>> getSharedWithMeWishlists(){
        return ResponseEntity.ok(wishlistService.getSharedWithMeWishlists());
    }

    @GetMapping("/public")
    public ResponseEntity<List<WishlistDto>> getPublicWishlists(){
        return ResponseEntity.ok(wishlistService.getPublicWishlists());
    }

    @GetMapping("/my-wishlist")
    public ResponseEntity<List<WishlistItemDto>> getMyWishlist(){
        return ResponseEntity.ok(wishlistService.getMyWishlist());
    }

}
