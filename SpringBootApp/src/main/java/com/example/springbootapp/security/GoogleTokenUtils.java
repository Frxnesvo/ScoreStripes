package com.example.springbootapp.security;

import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.service.impl.UserDetailsServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class GoogleTokenUtils{
    private static final String CLIENT_ID = "774497332630-a09mg8j2dd4va1gkis6m46u3l0tpr2lo.apps.googleusercontent.com";     //TODO: mettere nel properties

    public static GoogleIdToken getTokenByHeader(String header) throws GeneralSecurityException, IOException {
        if(header != null && header.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
            System.out.println("header ok");
            String token = header.substring("Bearer ".length());
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            return verifier.verify(token);
        }
        System.out.println("header not ok");
        return null;
    }

}
