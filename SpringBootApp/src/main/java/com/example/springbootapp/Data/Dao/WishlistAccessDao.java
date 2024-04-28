package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.WishlistAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistAccessDao extends JpaRepository<WishlistAccess, String> {
}
