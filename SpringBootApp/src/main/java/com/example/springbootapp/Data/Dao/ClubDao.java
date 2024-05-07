package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDao extends JpaRepository<Club, String> {
    boolean existsByName(String name);
}
