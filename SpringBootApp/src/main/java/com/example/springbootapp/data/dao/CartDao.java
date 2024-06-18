package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDao extends JpaRepository<Cart, String> {
    Optional<Cart> findById(String id);
}
