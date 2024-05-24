package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.WishlistShareToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistShareTokenDao extends JpaRepository<WishlistShareToken, String> {
    Optional<WishlistShareToken> findByToken(String token);
}

