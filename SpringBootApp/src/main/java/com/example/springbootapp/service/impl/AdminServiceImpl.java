package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.AdminDao;
import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.dto.AdminCreateRequestDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.Enums.Role;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.exceptions.InvalidTokenException;
import com.example.springbootapp.exceptions.UserAlreadyExistsException;
import com.example.springbootapp.security.GoogleTokenUtils;
import com.example.springbootapp.security.JwtUtils;
import com.example.springbootapp.security.SecurityConstants;
import com.example.springbootapp.service.interfaces.AdminService;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;

    private final AwsS3Service awsS3Service;

    private final AdminDao adminDao;

    private final GoogleTokenUtils googleTokenUtils;

    @Override
    public Admin register(AdminCreateRequestDto userDto, String header, HttpServletResponse response) {
        try{
            GoogleIdToken idToken = googleTokenUtils.getTokenByHeader(header);
            if(idToken != null){
                String username = userDto.getUsername();
                if(!userDao.existsByUsername(username)) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    String email = payload.getEmail();
                    String firstName = (String) payload.get("given_name");
                    String lastName = (String) payload.get("family_name");

                    Admin user = new Admin();
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setRole(Role.ADMIN);
                    user.setBirthDate(userDto.getBirthDate());
                    user.setGender(userDto.getGender());
                    user.setUsername(username);
                    user.setProfilePicUrl(awsS3Service.uploadFile(userDto.getImageProfile(), "profilePics", user.getUsername()));
                    adminDao.save(user);
                    addToken(response, user);
                    System.out.println(user);
                    return user;
                }
                else{
                    throw new UserAlreadyExistsException("User already exists");
                }
            }
            else{
                throw new InvalidTokenException("Invalid id token");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public Admin login(String header, HttpServletResponse response) {
//        try{
//            GoogleIdToken idToken = googleTokenUtils.getTokenByHeader(header);
//            if(idToken != null){
//                String email = idToken.getPayload().getEmail();
//                Optional<Admin> optionalAdmin = adminDao.findByEmail(email);
//                if(optionalAdmin.isPresent()){
//                    Admin admin =  optionalAdmin.get();
//                    addToken(response, admin);
//                    return admin;
//                }
//                return null;
//            }
//            else{
//                throw new InvalidTokenException("Invalid id token");
//            }
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void addToken(HttpServletResponse response, Admin admin) {
        String accessToken = JwtUtils.createAccessToken(admin.getId(), "", List.of("ROLE_" + admin.getRole().name()));
        response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_TOKEN_PREFIX + accessToken);
    }

    @Override
    public Admin login(String email, HttpServletResponse response, OAuth2AuthorizedClient oauth2AuthorizedClient) {
        Optional<Admin> admin = adminDao.findByEmail(email);

        if(admin.isPresent()) {
            Admin a = admin.get();
            addToken(response, a);
            return a;
        }
        else{
            String accessToken = oauth2AuthorizedClient.getAccessToken().getTokenValue();
            response.addHeader("access token", accessToken);
            return null;
        }
    }

}
