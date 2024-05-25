package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AddToWishlistRequestDto;
import com.example.springbootapp.data.dto.WishlistDto;
import com.example.springbootapp.data.dto.WishlistItemDto;
import com.example.springbootapp.data.dto.WishlistVisibilityDto;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;

import java.util.List;

public interface WishlistService {
    void changeVisibility(WishlistVisibilityDto visibilityDto);

    void addItemToWishlist(AddToWishlistRequestDto requestDto);

    List<WishlistItemDto> getMyWishlist();

    List<WishlistDto> getPublicWishlists();

    List<WishlistDto> getSharedWithMeWishlists();
}
