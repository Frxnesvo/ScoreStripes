package com.example.springbootapp.security;

import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.service.impl.UserDetailsServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class GoogleTokenUtils{


    private final Environment environment;

    public GoogleIdToken getTokenByHeader(String header) throws GeneralSecurityException, IOException {
        String clientId = environment.getProperty("client-id");
        if(header != null && header.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
            System.out.println("header ok");
            String token = header.substring("Bearer ".length());
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            return verifier.verify(token);
        }
        return null;
    }

}
