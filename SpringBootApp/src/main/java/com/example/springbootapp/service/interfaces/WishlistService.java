package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.*;

import java.util.List;

public interface WishlistService {
    void changeVisibility(WishlistVisibilityDto visibilityDto);

    WishlistItemDto addItemToWishlist(AddToWishlistRequestDto requestDto);

    WishlistDto getMyWishlist();

    List<WishlistDto> getPublicWishlists();

    List<WishlistDto> getSharedWithMeWishlists();

    List<CustomerSummaryDto> getMyWishlistAccesses();

    void deleteWishlistAccess(String guestId);

    void deleteItemFromWishlist(String productId);
}
