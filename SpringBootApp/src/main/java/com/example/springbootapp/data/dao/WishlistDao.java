package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistDao extends JpaRepository<Wishlist, String> {
}