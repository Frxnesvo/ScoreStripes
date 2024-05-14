package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, String> {
}
