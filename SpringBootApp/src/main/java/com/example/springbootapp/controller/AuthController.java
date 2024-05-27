package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.AdminCreateDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.Enums.Role;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.security.JwtUtils;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.security.SecurityConstants;
import com.example.springbootapp.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AdminServiceImpl adminService;

    @GetMapping("/admin-login")
    public ResponseEntity<?> checkAdminLogin(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response){

        String email = oauth2User.getAttributes().get("email").toString();
        Optional<User> user = adminService.findByEmail(email);
        if(user.isPresent()){
            User admin = user.get();
            CustomUserDetails userDetails = new CustomUserDetails(admin);
            if(admin.getRole().equals(Role.ADMIN)){
                //TODO gestire le authorities, in quanto createAccessToken richiede List<String>
                //String accessToken = JwtUtils.createAccessToken(userDetails.getID(), "", userDetails.getAuthorities());
                //response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + accessToken);
                return ResponseEntity.ok(admin);
            }
            return ResponseEntity.status(403).body("Access denied");

        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/admin-register")
    @ResponseStatus(HttpStatus.CREATED)
    //TODO mettere il dto
    public ResponseEntity<?> adminRegister(@AuthenticationPrincipal OAuth2User oauth2User, @RequestBody AdminCreateDto userDto) {

        User user = adminService.createAdmin(oauth2User, userDto);
        //TODO gestire immagine profilo
        CustomUserDetails userDetails = new CustomUserDetails(user);

        //String accessToken = JwtUtils.createAccessToken(userDetails.getID(), "", userDetails.getAuthorities());
        //response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + accessToken);

        adminService.save(user);
        return ResponseEntity.ok(user);
    }
}
