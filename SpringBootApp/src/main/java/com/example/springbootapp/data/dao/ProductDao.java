package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {
}
