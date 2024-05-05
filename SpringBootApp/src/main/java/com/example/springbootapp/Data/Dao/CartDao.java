package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<Cart, String> {
}