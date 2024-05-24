package com.example.springbootapp.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleTokenUtils{
    private static final String CLIENT_ID = "";     //TODO: mettere nel properties

    public static String getEmailFromToken(GoogleIdToken token) {
        GoogleIdToken.Payload payload = token.getPayload();
        return payload.getEmail();
    }

    public static GoogleIdToken verifyIdToken(String token) throws IOException, GeneralSecurityException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        return verifier.verify(token);
    }
}
