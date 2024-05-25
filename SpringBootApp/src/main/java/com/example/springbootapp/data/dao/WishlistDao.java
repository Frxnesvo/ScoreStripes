package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import com.example.springbootapp.data.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistDao extends JpaRepository<Wishlist, String> {
    List<Wishlist> findAllByVisibility(WishlistVisibility wishlistVisibility);
}

