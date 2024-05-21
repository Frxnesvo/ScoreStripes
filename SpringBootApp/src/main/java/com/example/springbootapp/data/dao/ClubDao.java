package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubDao extends JpaRepository<Club, String>, JpaSpecificationExecutor<Club> {
    Boolean existsByName(String name);
    @Query("SELECT c.name FROM Club c")
    List<String> getAllNames();
    Optional<Club> findByName(String name);
}
