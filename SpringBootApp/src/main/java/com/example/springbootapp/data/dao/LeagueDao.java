package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueDao extends JpaRepository<League, String> {
    Boolean existsByName(String name);
    @Query("SELECT l.name FROM League l")
    List<String> getAllNames();
    League findByName(String name);
}
