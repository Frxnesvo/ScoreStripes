package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {
}
