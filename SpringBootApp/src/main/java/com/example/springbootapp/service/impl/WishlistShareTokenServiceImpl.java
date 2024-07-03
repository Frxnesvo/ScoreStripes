package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.WishlistAccessDao;
import com.example.springbootapp.data.dao.WishlistDao;
import com.example.springbootapp.data.dao.WishlistShareTokenDao;
import com.example.springbootapp.data.dto.WishlistDto;
import com.example.springbootapp.data.dto.WishlistShareTokenDto;
import com.example.springbootapp.data.entities.Customer;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.data.entities.Wishlist;
import com.example.springbootapp.data.entities.WishlistAccess;
import com.example.springbootapp.data.entities.WishlistShareToken;
import com.example.springbootapp.exceptions.IllegalWishlistVisibility;
import com.example.springbootapp.exceptions.InvalidTokenException;
import com.example.springbootapp.exceptions.TokenExpiredException;
import com.example.springbootapp.service.interfaces.WishlistShareTokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistShareTokenServiceImpl implements WishlistShareTokenService {

    private final WishlistDao wishlistDao;
    private final WishlistShareTokenDao wishlistShareTokenDao;
    private final WishlistAccessDao wishlistAccessDao;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    @Override
    public WishlistShareTokenDto createShareToken() {
        Wishlist wishlist = wishlistDao.findById(((Customer) userDetailsService.getCurrentUser()).getWishlist().getId()).orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));
        if(wishlist.getVisibility()== WishlistVisibility.PRIVATE){
            throw new IllegalWishlistVisibility("Cannot share a private wishlist");
        }
        WishlistShareToken wishlistShareToken = new WishlistShareToken();
        wishlistShareToken.setToken(UUID.randomUUID().toString());
        wishlistShareToken.setExpirationDate(LocalDateTime.now().plusHours(24));
        wishlistShareToken.setWishlist(wishlist);
        wishlistShareToken= wishlistShareTokenDao.save(wishlistShareToken);
        return modelMapper.map(wishlistShareToken, WishlistShareTokenDto.class);
    }


    @Override
    @Transactional
    public WishlistDto validateShareAccess(String token) {
        System.out.println("Token: "+token);
        WishlistShareToken wishlistShareToken = wishlistShareTokenDao.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Token not found"));
        if(wishlistShareToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }
        if(wishlistShareToken.getWishlist().getVisibility()== WishlistVisibility.PRIVATE) {
            throw new IllegalWishlistVisibility("Cannot access a private wishlist");
        }
        Customer guest = (Customer) userDetailsService.getCurrentUser();
        if(wishlistAccessDao.existsByWishlistIdAndGuestId(wishlistShareToken.getWishlist().getId(), guest.getId()) || wishlistShareToken.getWishlist().getId().equals(guest.getWishlist().getId())) {
            throw new InvalidTokenException("Already have access to this wishlist"); //TODO: maybe cambio eccezione
        }
        WishlistAccess wishlistAccess = new WishlistAccess();
        wishlistAccess.setGuest((Customer) userDetailsService.getCurrentUser());
        wishlistAccess.setWishlist(wishlistShareToken.getWishlist());
        wishlistAccessDao.save(wishlistAccess);
        wishlistShareTokenDao.delete(wishlistShareToken);
        return modelMapper.map(wishlistAccess.getWishlist(), WishlistDto.class);
    }
}
