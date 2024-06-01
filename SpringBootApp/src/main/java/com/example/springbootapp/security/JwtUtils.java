package com.example.springbootapp.security;

import com.example.springbootapp.data.entities.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtils {
    private static final SecretKey SECRET = new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET), "HmacSHA256");

    public static String createAccessToken(String userId, String issuer, List<String> roles){
        try{
            //creazione del payload
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userId)      //TODO: cambiare mettendo l'id dell'utente invece che lo username
                    .issuer(issuer)
                    .claim("roles", roles)
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(SecurityConstants.EXPIRATION_TIME)))
                    .build();

            Payload payload = new Payload(claims.toJSONObject());

            //JSON Web signature per firmare il token

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);    //aggiungo al JWS un
                                                                                                //header in cui
                                                                                                //specifico l'algoritmo
                                                                                                //di firma utilizzato, e
                                                                                                //ci aggiungo anche il payload

            jwsObject.sign(new MACSigner(SECRET));      //firma il JWS utilizzando un MACSigner, utilizzato per le firme
                                                        // di tipo HMAC

            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error to create JWT", e);
        }
    }

    public static String createRefreshToken(String userId){
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userId)
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(SecurityConstants.EXPIRATION_REFRESH_TOKEN_TIME)))
                    .build();

            Payload payload = new Payload(claims.toJSONObject());

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
            jwsObject.sign(new MACSigner(SECRET));
            return  jwsObject.serialize();
        }catch(JOSEException e){
            throw new RuntimeException("Error to create JWT", e);
        }
    }

    public static UsernamePasswordAuthenticationToken parseToken(String token) throws ParseException, JOSEException, BadJOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        signedJWT.verify(new MACVerifier(SECRET));
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.HS256,
                new ImmutableSecret<>(SECRET));
        jwtProcessor.setJWSKeySelector(keySelector);
        jwtProcessor.process(signedJWT, null);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String userId = claims.getSubject();
        var roles = (List<String>) claims.getClaim("roles");
        var authorities = roles == null ? null : roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

}
