package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.WishlistDao;
import com.example.springbootapp.data.dto.WishlistVisibilityDto;
import com.example.springbootapp.data.entities.Cart;
import com.example.springbootapp.data.entities.Customer;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.data.entities.Wishlist;
import com.example.springbootapp.service.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserDetailsServiceImpl userDetailsService;
    private final WishlistDao wishlistDao;

    @Override  //TODO: proteggere da admin
    public void changeVisibility(WishlistVisibilityDto visibilityDto) {
        Wishlist wishlist =((Customer) userDetailsService.getCurrentUser()).getWishlist();
        wishlist.setVisibility(visibilityDto.getVisibility());
        wishlistDao.save(wishlist);
    }
}
