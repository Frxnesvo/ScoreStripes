package com.example.springbootapp.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleTokenVerifier extends UsernamePasswordAuthenticationFilter {
    private static final String CLIENT_ID = "";     //TODO: mettere nel properties
    public static UsernamePasswordAuthenticationToken parseToken(String token) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                //.setAudience(Arrays.asList(CUSTOMER_CLIENT_ID, ADMIN_CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if(idToken != null){
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            //TODO: vedere come gestire i ruoli
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        }
        else throw new GeneralSecurityException("Invalid ID token");
    }
}
