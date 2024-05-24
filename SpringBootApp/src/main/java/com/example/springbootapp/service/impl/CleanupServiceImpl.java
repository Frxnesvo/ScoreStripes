package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.WishlistShareTokenDao;
import com.example.springbootapp.service.interfaces.CleanupService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CleanupServiceImpl implements CleanupService {

    private final WishlistShareTokenDao wishlistShareTokenDao;

    @Override
    @Scheduled(fixedRateString = "${cleanup.rate}")
    public void cleanupExpiredTokens() {          //potrei fare il metodo della repository int cosi che ritorni il numero di token eliminati e loggarlo
        wishlistShareTokenDao.deleteByExpirationDateBefore(LocalDateTime.now());
    }
}
