package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.ProductWithVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWithVariantDao extends JpaRepository<ProductWithVariant, String> {
}
