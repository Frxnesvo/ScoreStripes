package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, String> {
}
