package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.AccessTokenBlacklistDao;
import com.example.springbootapp.data.entities.AccessTokenBlacklist;
import com.example.springbootapp.exceptions.InvalidTokenException;
import com.example.springbootapp.handler.JwtHandler;
import com.example.springbootapp.service.interfaces.AccessTokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccessTokenBlacklistServiceImpl implements AccessTokenBlacklistService {
    private final AccessTokenBlacklistDao accessTokenBlacklistDao;
    private final JwtHandler jwtHandler;


    @Override
    public void blacklistToken(String jwt) {
        if(StringUtils.hasText(jwt)){
            Date expirationDate = jwtHandler.getExpirationDateFromJwt(jwt);
            AccessTokenBlacklist accessTokenBlacklist = new AccessTokenBlacklist();
            accessTokenBlacklist.setToken(jwt);
            accessTokenBlacklist.setExpirationDate(expirationDate);
            accessTokenBlacklistDao.save(accessTokenBlacklist);
        }
        else
            throw new InvalidTokenException("Token is null");
    }

    @Override
    public Boolean isTokenBlacklisted(String jwt) {
        return accessTokenBlacklistDao.existsByToken(jwt);
    }
}
