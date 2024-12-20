package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.ProductPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPicDao extends JpaRepository<ProductPic, String> {
}
