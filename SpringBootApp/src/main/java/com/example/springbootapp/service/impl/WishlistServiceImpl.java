package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.dao.WishlistAccessDao;
import com.example.springbootapp.data.dao.WishlistDao;
import com.example.springbootapp.data.dao.WishlistItemDao;
import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.exceptions.IllegalWishlistVisibility;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.service.interfaces.WishlistService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
    public WishlistItemDto addItemToWishlist(AddToWishlistRequestDto requestDto) {
        Product product= productDao.findById(requestDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Wishlist wishlist =((Customer) userDetailsService.getCurrentUser()).getWishlist();
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setDateAdded(LocalDate.now());
        wishlistItem.setWishlist(wishlist);
        wishlistItem.setProduct(product);
        wishlistItemDao.save(wishlistItem);  //Ho preferito iniettare anche il dao per il WishlistItem per evitare di fare una save di wishlist e usare il cascade poichè meno efficiente
        return modelMapper.map(wishlistItem, WishlistItemDto.class);
    }

    @Override
    public WishlistDto getMyWishlist() {
        Wishlist wishlist= wishlistDao.findById(((Customer) userDetailsService.getCurrentUser()).getWishlist().getId()).orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));
        Hibernate.initialize(wishlist.getItems());
        Hibernate.initialize(wishlist.getOwner());  //TODO: TEST
        return modelMapper.map(wishlist, WishlistDto.class);
    }

    @Override
    public List<WishlistDto> getPublicWishlists() {
        List<Wishlist> wishlists = wishlistDao.findAllByVisibility(WishlistVisibility.PUBLIC);

        wishlists.forEach(wishlist -> Hibernate.initialize(wishlist.getOwner())); //TODO: TEST

        return wishlists.stream()
                .map(wishlist -> modelMapper.map(wishlist, WishlistDto.class))
                .toList();
    }

    @Override
    public List<WishlistDto> getSharedWithMeWishlists() {
        List<WishlistAccess> wishlistAccesses = wishlistAccessDao.findAllByGuestId(userDetailsService.getCurrentUser().getId());
        wishlistAccesses.forEach(wishlistAccess -> Hibernate.initialize(wishlistAccess.getWishlist().getOwner()));  //TODO: TEST
        return wishlistAccesses.stream()
                .map(wishlistAccess -> modelMapper.map(wishlistAccess.getWishlist(), WishlistDto.class))
                .toList();
    }

    @Override
    public List<CustomerSummaryDto> getMyWishlistAccesses() {
        List<WishlistAccess> wishlistAccesses = wishlistAccessDao.findAllByWishlistId(((Customer) userDetailsService.getCurrentUser()).getWishlist().getId());
        return wishlistAccesses.stream()
                .map(wishlistAccess -> modelMapper.map(wishlistAccess.getGuest(), CustomerSummaryDto.class))
                .toList();
    }

    @Override
    public void deleteWishlistAccess(String guestId) {
        WishlistAccess wishlistAccess = wishlistAccessDao.findByWishlistIdAndGuestId(((Customer) userDetailsService.getCurrentUser()).getWishlist().getId(), guestId)
                .orElseThrow(() -> new EntityNotFoundException("Access not found"));
        wishlistAccessDao.delete(wishlistAccess);
    }

    @Override
    public void deleteItemFromWishlist(String productId) {
        Customer customer = (Customer) userDetailsService.getCurrentUser();
        //cerca nella wishlist un item che ha un prodotto con l'id passato`usando gli stream e se lo trova lo rimuove
        WishlistItem item = customer.getWishlist().getItems().stream()
                .filter(wishlistItem -> wishlistItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RequestValidationException("product not found in wishlist"));
        customer.getWishlist().getItems().remove(item);
        wishlistDao.save(customer.getWishlist());
    }
}
