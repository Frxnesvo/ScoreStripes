package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.dao.WishlistAccessDao;
import com.example.springbootapp.data.dao.WishlistDao;
import com.example.springbootapp.data.dao.WishlistItemDao;
import com.example.springbootapp.data.dto.AddToWishlistRequestDto;
import com.example.springbootapp.data.dto.WishlistDto;
import com.example.springbootapp.data.dto.WishlistItemDto;
import com.example.springbootapp.data.dto.WishlistVisibilityDto;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.exceptions.IllegalWishlistVisibility;
import com.example.springbootapp.service.interfaces.WishlistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserDetailsServiceImpl userDetailsService;
    private final WishlistDao wishlistDao;
    private final ProductDao productDao;
    private final WishlistItemDao wishlistItemDao;
    private final WishlistAccessDao wishlistAccessDao;
    private final ModelMapper modelMapper;

    @Override  //TODO: proteggere da admin
    public void changeVisibility(WishlistVisibilityDto visibilityDto) {
        Wishlist wishlist =((Customer) userDetailsService.getCurrentUser()).getWishlist();
        if(wishlist.getVisibility()== visibilityDto.getVisibility()){
            throw new IllegalWishlistVisibility("Wishlist already has this visibility");
        }
        if(visibilityDto.getVisibility()== WishlistVisibility.PRIVATE){
            wishlistAccessDao.deleteByWishlistId(wishlist.getId());  //deleto tutti gli accessi se la wishlist diventa privata
        }
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

    @Override
    public List<WishlistItemDto> getMyWishlist() {
        List<WishlistItem> wishlistItems = wishlistItemDao.findAllByWishlistId(((Customer) userDetailsService.getCurrentUser()).getWishlist().getId());
        return wishlistItems.stream()
                .map(wishlistItem -> modelMapper.map(wishlistItem, WishlistItemDto.class))
                .toList();
    }

    @Override
    public List<WishlistDto> getPublicWishlists() {
        List<Wishlist> wishlists = wishlistDao.findAllByVisibility(WishlistVisibility.PUBLIC);
        return wishlists.stream()
                .map(wishlist -> modelMapper.map(wishlist, WishlistDto.class))
                .toList();
    }

    @Override
    public List<WishlistDto> getSharedWithMeWishlists() {
        List<WishlistAccess> wishlistAccesses = wishlistAccessDao.findAllByGuestId(userDetailsService.getCurrentUser().getId());
        return wishlistAccesses.stream()
                .map(wishlistAccess -> modelMapper.map(wishlistAccess.getWishlist(), WishlistDto.class))
                .toList();
    }
}
