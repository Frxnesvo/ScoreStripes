package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.AdminCreateDto;
import com.example.springbootapp.data.entities.RegistrationToken;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.security.GoogleTokenUtils;
import com.example.springbootapp.security.JwtUtils;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.impl.AdminServiceImpl;
import com.example.springbootapp.service.impl.RegistrationTokenServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AdminServiceImpl adminService;
    private final RegistrationTokenServiceImpl registrationTokenService;

    @GetMapping("/admin-login")
    public ResponseEntity<?> checkAdminLogin(
            @RegisteredOAuth2AuthorizedClient() OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User,
            HttpServletResponse response
    ){
        System.out.println("stampa");
        String email = oauth2User.getAttributes().get("email").toString();
        Optional<User> user = adminService.findByEmail(email);
        if(user.isPresent()){
            User admin = user.get();
            CustomUserDetails userDetails = new CustomUserDetails(admin);
            //TODO gestire le authorities, in quanto createAccessToken richiede List<String>
            //String accessToken = JwtUtils.createAccessToken(userDetails.getID(), "", userDetails.getAuthorities());
            //response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + accessToken);
            return ResponseEntity.ok(admin);
        }
        String token = registrationTokenService.createRegistrationToken(oauth2User);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(token);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        System.out.println("Entrato nel login");

        Optional<User> user = adminService.getUserByHeader(authHeader);
        if(user.isPresent()){
            User u = user.get();
            CustomUserDetails userDetails = new CustomUserDetails(u);
            //String accessToken = JwtUtils.createAccessToken(userDetails.getID(), "", userDetails.getAuthorities());
            //response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + accessToken);
            return ResponseEntity.ok(u);
        }
        System.out.println("user not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin-register")
    @ResponseStatus(HttpStatus.CREATED)
    //TODO mettere il dto
    public ResponseEntity<?> adminRegister(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, AdminCreateDto userDto) {

        User user = adminService.createAdmin(userDto, authHeader);

        //String accessToken = JwtUtils.createAccessToken(userDetails.getID(), "", userDetails.getAuthorities());
        //response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + accessToken);
        return ResponseEntity.ok(user);
    }
}