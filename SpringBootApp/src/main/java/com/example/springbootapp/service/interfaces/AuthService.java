package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.*;
import com.google.auth.oauth2.TokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    AuthResponseDto login(String idToken,String userType) throws GeneralSecurityException, IOException, TokenVerifier.VerificationException;

    String registerAdmin(AdminRegisterDto adminRegisterDto);
    String registerCustomer(CustomerRegisterDto customerRegisterDto);
    String logout(String jwtFromRequest);
}
