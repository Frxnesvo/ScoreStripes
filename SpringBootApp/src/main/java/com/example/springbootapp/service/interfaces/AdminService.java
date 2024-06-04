package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AdminCreateRequestDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import java.util.Optional;

public interface AdminService {

    Admin register(AdminCreateRequestDto adminDto, String header, HttpServletResponse response);

    void addToken(HttpServletResponse response, Admin admin);

    Admin login(String email, HttpServletResponse response, OAuth2AuthorizedClient oauth2AuthorizedClient);


}
