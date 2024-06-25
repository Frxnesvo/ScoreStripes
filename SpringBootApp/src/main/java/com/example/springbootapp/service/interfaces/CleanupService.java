package com.example.springbootapp.service.interfaces;

public interface CleanupService {
    void cleanupExpiredWishlistAccessTokens();
    void cleanupExpiredAccessTokens();
}
