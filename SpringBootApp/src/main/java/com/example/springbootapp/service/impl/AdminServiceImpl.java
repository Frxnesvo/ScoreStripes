package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
