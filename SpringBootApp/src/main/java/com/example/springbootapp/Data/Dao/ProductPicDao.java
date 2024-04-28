package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.ProductPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPicDao extends JpaRepository<ProductPic, String> {
}
