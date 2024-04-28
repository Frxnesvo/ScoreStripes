package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemDao extends JpaRepository<WishlistItem, String> {
}
