package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.AccessTokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AccessTokenBlacklistDao extends JpaRepository<AccessTokenBlacklist, String> {
    Boolean existsByToken(String token);
    void deleteByExpirationDateBefore(Date now);
}
