package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.dto.AuthResponseDto;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.exceptions.NoAccountException;
import com.example.springbootapp.exceptions.VerificationException;
import com.example.springbootapp.service.interfaces.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.TokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.springbootapp.handler.JwtHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${google.clientId}")
    private String googleClientId;

    private final UserDao userDao;
    private final JwtHandler jwtHandler;


    @Override
    public AuthResponseDto login(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();{
        }
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                throw new VerificationException("Invalid token");
            }
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            Optional<User> user = userDao.findByEmail(payload.getEmail());
            if (user.isPresent()) {
                String jwtToken = jwtHandler.generateJwtToken(user.get());
                return new AuthResponseDto(jwtToken,"Login successful");
            }
            else {
                throw new NoAccountException("No account found");
            }
        }
        catch (GeneralSecurityException | IOException | VerificationException e) {
            throw new VerificationException("Invalid token");
        }
    }
}
