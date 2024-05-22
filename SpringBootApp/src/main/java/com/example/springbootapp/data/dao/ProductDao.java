package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {
    List<Product> findByClubName(String clubName);
}
