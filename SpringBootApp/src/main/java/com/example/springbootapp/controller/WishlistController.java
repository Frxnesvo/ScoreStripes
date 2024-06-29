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
import java.util.Map;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/wishlists")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final WishlistShareTokenService wishlistShareTokenService;

    @PatchMapping("/change-visibility")
    public ResponseEntity<Map<String,String>> changeVisibility(@Valid @RequestBody WishlistVisibilityDto visibilityDto){
        System.out.println(visibilityDto.getVisibility().name());
        wishlistService.changeVisibility(visibilityDto);
        return ResponseEntity.ok(Map.of("message","Visibility changed"));
    }

    @PostMapping("/create-shareToken")
    public ResponseEntity<WishlistShareTokenDto> createShareToken(){
        return ResponseEntity.ok(wishlistShareTokenService.createShareToken());
    }

    @PostMapping("/validate-shareAccess")
    public ResponseEntity<WishlistDto> validateShareAccess(@RequestBody Map<String, String> token){  //TODO: Ha senso inglobare il token in un Dto e validarlo per evitare query?
        return ResponseEntity.ok(wishlistShareTokenService.validateShareAccess(token.get("message")));
    }

    @PostMapping("/add-item")
    public ResponseEntity<WishlistItemDto> addItemToWishlist(@Valid @RequestBody AddToWishlistRequestDto requestDto){
         return ResponseEntity.ok(wishlistService.addItemToWishlist(requestDto));
    }

    @GetMapping("/shared-with-me")
    public ResponseEntity<List<WishlistDto>> getSharedWithMeWishlists(){
        return ResponseEntity.ok(wishlistService.getSharedWithMeWishlists());
    }

    @GetMapping("/public")    //TODO: Farla paginata
    public ResponseEntity<List<WishlistDto>> getPublicWishlists(){
        return ResponseEntity.ok(wishlistService.getPublicWishlists());
    }

    @GetMapping("/my-wishlist")
    public ResponseEntity<WishlistDto> getMyWishlist(){
        return ResponseEntity.ok(wishlistService.getMyWishlist());
    }

    @GetMapping("/my-wishlist/accesses")
    public ResponseEntity<List<CustomerSummaryDto>> getMyWishlistAccesses(){
        return ResponseEntity.ok(wishlistService.getMyWishlistAccesses());
    }

    @DeleteMapping("/my-wishlist/accesses/{GuestId}")
    public ResponseEntity<Map<String, String>> deleteWishlistAccess(@PathVariable String GuestId) {
        wishlistService.deleteWishlistAccess(GuestId);
        return ResponseEntity.ok(Map.of("response", "Access deleted"));
    }

    @DeleteMapping("/my-wishlist/item/{productId}")
    public ResponseEntity<String> deleteItemFromWishlist(@PathVariable String productId){
        wishlistService.deleteItemFromWishlist(productId);
        return ResponseEntity.ok("Item deleted");
    }
}
