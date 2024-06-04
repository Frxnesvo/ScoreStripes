package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.AdminCreateRequestDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.security.JwtUtils;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.security.SecurityConstants;
import com.example.springbootapp.service.impl.AdminServiceImpl;
import com.example.springbootapp.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AdminServiceImpl adminService;

//    @GetMapping("/admin-login1234")
//    public ResponseEntity<?> adminLogin(HttpServletResponse response, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
//        Admin admin = adminService.login(authHeader, response);
//        if(admin != null) return ResponseEntity.ok(admin);
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping(value = "/admin-register", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Admin> adminRegister(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @Valid @ModelAttribute AdminCreateRequestDto userDto,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        if(bindingResult.hasErrors())
            throw new RequestValidationException("Input validation failed");

        Admin user = adminService.register(userDto, authHeader, response);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin-login")
    public ResponseEntity<?> login(
            HttpServletResponse response,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User
    ){
        Admin admin = adminService.login(oauth2User.getAttribute("email").toString(), response, authorizedClient);
        if(admin != null)return ResponseEntity.ok(admin);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }

}