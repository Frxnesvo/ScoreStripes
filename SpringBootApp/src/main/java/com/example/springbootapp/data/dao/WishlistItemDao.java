package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemDao extends JpaRepository<WishlistItem, String> {
}
