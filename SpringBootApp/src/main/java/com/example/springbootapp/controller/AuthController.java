package com.example.springbootapp.controller;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.security.GoogleTokenUtils;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.impl.AdminServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AdminServiceImpl adminService;

    @GetMapping("/admin_login")
    public ResponseEntity<?> checkAdminLogin(@RequestHeader("Authorization") String authorizationHeader){

        String bearer = "Bearer ";
        if (authorizationHeader == null || !authorizationHeader.startsWith(bearer))
            return ResponseEntity.status(400).body("Invalid authorization header");

        String tokenString = authorizationHeader.substring(bearer.length());

        try{
            GoogleIdToken idToken = GoogleTokenUtils.verifyIdToken(tokenString);

            if(idToken != null){
                String email = GoogleTokenUtils.getEmailFromToken(idToken);
                Optional<User> admin = adminService.findByEmail(email);
                if(admin.isPresent()){
                    //TODO creazione access token
                    return ResponseEntity.ok(admin.get());
                }
                return ResponseEntity.notFound().build();
            }
            else return ResponseEntity.status(401).body("Invalid token");

        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(500).body("error during the token processing");
        }
    }
}
