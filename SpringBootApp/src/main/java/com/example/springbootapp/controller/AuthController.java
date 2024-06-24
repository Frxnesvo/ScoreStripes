package com.example.springbootapp.controller;


import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.exceptions.InvalidTokenException;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.service.impl.UserDetailsServiceImpl;
import com.example.springbootapp.utils.ExceptionUtils;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.AuthService;
import com.google.auth.oauth2.TokenVerifier;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
        private final ExceptionUtils exceptionUtils;

        @PostMapping("/login/{userType}")
        public ResponseEntity<AuthResponseDto> login(@RequestBody Map<String,String> request, @PathVariable("userType") String userType) throws GeneralSecurityException, TokenVerifier.VerificationException, IOException {
            String idToken = request.get("idToken");
            if(idToken == null){
                throw new InvalidTokenException("Token is null");
            }
            AuthResponseDto response =authService.login(idToken,userType);
            return ResponseEntity.ok(response);
        }

        @PostMapping(path = "/register-admin", consumes = {"multipart/form-data"})
        public ResponseEntity<Map<String,String>> registerAdmin(@Valid @ModelAttribute AdminRegisterDto adminRegisterDto, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
            }

            return ResponseEntity.ok(Map.of("message",authService.registerAdmin(adminRegisterDto)));
        }

        @PostMapping(path = "/register-customer", consumes = {"multipart/form-data"})
        public ResponseEntity<Map<String,String>> registerCustomer(@Valid @ModelAttribute CustomerRegisterDto customerRegisterDto, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
            }

            return ResponseEntity.ok(Map.of("message",authService.registerCustomer(customerRegisterDto)));
        }







}


