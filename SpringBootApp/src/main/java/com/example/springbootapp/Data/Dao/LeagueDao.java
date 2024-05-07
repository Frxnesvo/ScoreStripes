package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueDao extends JpaRepository<League, String> {
    boolean existsByName(String name);
    List<String> getAllNames();
}
