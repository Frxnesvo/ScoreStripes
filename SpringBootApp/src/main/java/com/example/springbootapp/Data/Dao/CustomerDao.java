package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {
    Long countAllByCreatedDateAfter(LocalDateTime date);
}
