package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
}
