package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, String> {
    List<Order> findByCustomerId(String customerId);
}
