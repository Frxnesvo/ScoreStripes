package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistDao extends JpaRepository<Wishlist, String> {
}
