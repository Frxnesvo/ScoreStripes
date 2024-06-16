package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.AdminRegisterDto;
import com.example.springbootapp.data.dto.AuthResponseDto;
import com.example.springbootapp.data.dto.CustomerRegisterDto;
import com.google.auth.oauth2.TokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    AuthResponseDto login(String idToken) throws GeneralSecurityException, IOException, TokenVerifier.VerificationException;

    void registerAdmin(AdminRegisterDto adminRegisterDto);
    void registerCustomer(CustomerRegisterDto customerRegisterDto);
}
