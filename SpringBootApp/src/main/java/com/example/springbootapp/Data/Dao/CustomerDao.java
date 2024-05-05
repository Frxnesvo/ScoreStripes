package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {
    List<Customer> findAll();
}
