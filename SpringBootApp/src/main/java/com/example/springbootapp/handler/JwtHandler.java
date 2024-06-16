package com.example.springbootapp.handler;

import com.example.springbootapp.data.entities.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtHandler {

    @Value("${jwt.secret}")
    private String secret;

    private final Long expirationTime = (long) (1000 * 60 * 60 * 24); // 24 hours


    public String generateJwtToken(User user) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .claim("role", user.getRole().name())
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claims);

            JWSSigner signer = new MACSigner(secret.getBytes());
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error while generating JWT token");  //TODO: da fare custom e gestire nel global exception handler
        }
    }

    public boolean validateToken(String authToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(authToken);
            MACVerifier verifier = new MACVerifier(secret);
            if(!signedJWT.verify(verifier)){
                return false;
            }
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expirationTime != null && !expirationTime.before(new Date());
        } catch (ParseException | JOSEException  e) {
            throw new RuntimeException("Invalid token"); //TODO: cambiare eccezione con una custom
        }
    }

    public String getEmailFromJwt(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token");  //TODO: cambiare eccezione con una custom
        }
    }
}
