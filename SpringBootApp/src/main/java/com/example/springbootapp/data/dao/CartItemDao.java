package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, String> {
    List<CartItem> findAllByCartId(String id);
}
