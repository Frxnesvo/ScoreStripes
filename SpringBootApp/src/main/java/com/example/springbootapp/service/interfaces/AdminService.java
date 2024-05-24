package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.User;

import java.util.Optional;

public interface AdminService {

    Optional<User> findByEmail(String email);
}
