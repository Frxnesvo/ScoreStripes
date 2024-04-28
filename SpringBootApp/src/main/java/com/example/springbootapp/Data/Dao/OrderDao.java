package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order, String> {
}
