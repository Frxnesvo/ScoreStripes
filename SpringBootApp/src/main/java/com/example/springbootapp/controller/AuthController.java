package com.example.springbootapp.controller;


import com.example.springbootapp.data.dto.AuthResponseDto;
import com.example.springbootapp.exceptions.InvalidTokenException;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.AuthService;
import com.google.auth.oauth2.TokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;


@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody Map<String,String> request) throws GeneralSecurityException, TokenVerifier.VerificationException, IOException {
        String idToken = request.get("idToken");
        if(idToken == null){
            throw new InvalidTokenException("Token is null");
        }
        AuthResponseDto response =authService.login(idToken);
        return ResponseEntity.ok(response);
    }


}


