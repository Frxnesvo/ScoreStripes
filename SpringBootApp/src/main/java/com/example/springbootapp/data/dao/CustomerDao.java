package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {
    Long countAllByCreatedDateAfter(LocalDateTime date);
}
