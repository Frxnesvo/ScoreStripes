package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.ProductWithVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWithVariantDao extends JpaRepository<ProductWithVariant, String> {
}
