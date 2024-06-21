package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.WishlistDto;
import com.example.springbootapp.data.dto.WishlistShareTokenDto;
import com.example.springbootapp.data.entities.Wishlist;
import com.example.springbootapp.data.entities.WishlistShareToken;

public interface WishlistShareTokenService {

    WishlistShareTokenDto createShareToken();
    WishlistDto validateShareAccess(String token);
}
