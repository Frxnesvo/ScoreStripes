package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.WishlistService;
import com.example.springbootapp.service.interfaces.WishlistShareTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/wishlists")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final WishlistShareTokenService wishlistShareTokenService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/change-visibility")
    public ResponseEntity<Map<String,String>> changeVisibility(@Valid @RequestBody WishlistVisibilityDto visibilityDto){
        wishlistService.changeVisibility(visibilityDto);
        return ResponseEntity.ok(Map.of("message","Visibility changed"));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create-shareToken")
    public ResponseEntity<WishlistShareTokenDto> createShareToken(){
        return ResponseEntity.ok(wishlistShareTokenService.createShareToken());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/validate-shareAccess")
    public ResponseEntity<WishlistDto> validateShareAccess(@RequestBody Map<String, String> token){
        return ResponseEntity.ok(wishlistShareTokenService.validateShareAccess(token.get("message")));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add-item")
    public ResponseEntity<WishlistItemDto> addItemToWishlist(@Valid @RequestBody AddToWishlistRequestDto requestDto){
         return ResponseEntity.ok(wishlistService.addItemToWishlist(requestDto));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/shared-with-me")
    public ResponseEntity<List<WishlistDto>> getSharedWithMeWishlists(){
        return ResponseEntity.ok(wishlistService.getSharedWithMeWishlists());
    }

    @GetMapping("/public")    //TODO: Farla paginata
    public ResponseEntity<List<WishlistDto>> getPublicWishlists(){
        return ResponseEntity.ok(wishlistService.getPublicWishlists());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-wishlist")
    public ResponseEntity<WishlistDto> getMyWishlist(){
        return ResponseEntity.ok(wishlistService.getMyWishlist());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-wishlist/accesses")
    public ResponseEntity<List<CustomerSummaryDto>> getMyWishlistAccesses(){
        return ResponseEntity.ok(wishlistService.getMyWishlistAccesses());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/my-wishlist/accesses/{GuestId}")
    public ResponseEntity<Map<String, String>> deleteWishlistAccess(@PathVariable String GuestId) {
        wishlistService.deleteWishlistAccess(GuestId);
        return ResponseEntity.ok(Map.of("response", "Access deleted"));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/my-wishlist/item/{productId}")
    public ResponseEntity<Map<String,String>> deleteItemFromWishlist(@PathVariable String productId){
        wishlistService.deleteItemFromWishlist(productId);
        return ResponseEntity.ok(Map.of("response", "Item deleted"));
    }
}
