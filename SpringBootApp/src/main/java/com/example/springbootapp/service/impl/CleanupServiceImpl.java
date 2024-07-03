package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.AccessTokenBlacklistDao;
import com.example.springbootapp.data.dao.WishlistShareTokenDao;
import com.example.springbootapp.service.interfaces.CleanupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CleanupServiceImpl implements CleanupService {

    private final WishlistShareTokenDao wishlistShareTokenDao;
    private final AccessTokenBlacklistDao accessTokenBlacklistDao;

    @Override
    @Scheduled(fixedRateString = "${cleanup.rate}")
    @Transactional
    public void cleanupExpiredWishlistAccessTokens() {          //potrei fare il metodo della repository int cosi che ritorni il numero di token eliminati e loggarlo
        wishlistShareTokenDao.deleteByExpirationDateBefore(LocalDateTime.now());
    }

    @Override
    @Scheduled(fixedRateString = "${cleanup.rate}")
    @Transactional
    public void cleanupExpiredAccessTokens() {
        accessTokenBlacklistDao.deleteByExpirationDateBefore(new Date());

    }
}
