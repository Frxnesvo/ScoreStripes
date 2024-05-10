package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubDao extends JpaRepository<Club, String> {
    Boolean existsByName(String name);
    @Query("SELECT c.name FROM Club c")
    List<String> getAllNames();
}
