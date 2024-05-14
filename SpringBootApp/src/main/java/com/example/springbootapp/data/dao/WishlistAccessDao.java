package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.WishlistAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistAccessDao extends JpaRepository<WishlistAccess, String> {
}
