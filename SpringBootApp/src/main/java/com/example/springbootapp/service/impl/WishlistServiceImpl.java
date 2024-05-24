package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.dao.WishlistDao;
import com.example.springbootapp.data.dao.WishlistItemDao;
import com.example.springbootapp.data.dto.AddToWishlistRequestDto;
import com.example.springbootapp.data.dto.WishlistVisibilityDto;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.service.interfaces.WishlistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserDetailsServiceImpl userDetailsService;
    private final WishlistDao wishlistDao;
    private final ProductDao productDao;
    private final WishlistItemDao wishlistItemDao;

    @Override  //TODO: proteggere da admin
    public void changeVisibility(WishlistVisibilityDto visibilityDto) {
        Wishlist wishlist =((Customer) userDetailsService.getCurrentUser()).getWishlist();
        wishlist.setVisibility(visibilityDto.getVisibility());
        wishlistDao.save(wishlist);
    }

    @Override
    public void addItemToWishlist(AddToWishlistRequestDto requestDto) {
        Product product= productDao.findById(requestDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Wishlist wishlist =((Customer) userDetailsService.getCurrentUser()).getWishlist();
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setDateAdded(LocalDate.now());
        wishlistItem.setWishlist(wishlist);
        wishlistItem.setProduct(product);
        wishlistItemDao.save(wishlistItem);  //Ho preferito iniettare anche il dao per il WishlistItem per evitare di fare una save di wishlist e usare il cascade poichè meno efficiente
    }
}
