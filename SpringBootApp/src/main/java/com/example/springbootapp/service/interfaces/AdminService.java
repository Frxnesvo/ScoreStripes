package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.dto.AdminCreateDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public interface AdminService {

    Optional<User> findByEmail(String email);

    User createAdmin(AdminCreateDto adminDto, String header);

    Optional<User> getUserByHeader(String header);
}
