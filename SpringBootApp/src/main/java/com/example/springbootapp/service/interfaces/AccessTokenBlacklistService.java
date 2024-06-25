package com.example.springbootapp.service.interfaces;

public interface AccessTokenBlacklistService {
    void blacklistToken(String jwt);
    Boolean isTokenBlacklisted(String jwt);
}
