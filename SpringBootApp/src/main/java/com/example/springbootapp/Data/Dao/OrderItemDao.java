package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, String> {
}
