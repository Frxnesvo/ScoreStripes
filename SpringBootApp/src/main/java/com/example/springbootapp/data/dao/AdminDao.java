package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, String> {
}
