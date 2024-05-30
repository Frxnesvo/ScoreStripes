package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.dto.AdminCreateDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.Enums.Role;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.security.GoogleTokenUtils;
import com.example.springbootapp.security.SecurityConstants;
import com.example.springbootapp.service.interfaces.AdminService;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;
    private final AwsS3Service awsS3Service;

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }


    @Override
    public User createAdmin(AdminCreateDto userDto, String header) {
        try{
            GoogleIdToken idToken = GoogleTokenUtils.getTokenByHeader(header);
            if(idToken != null){
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String firstName = (String) payload.get("given_name");
                String lastName = (String) payload.get("family_name");

                User user = new Admin();
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setRole(Role.ADMIN);
                user.setBirthDate(userDto.getBirthDate());
                user.setGender(userDto.getGender());
                user.setUsername(userDto.getUsername());
                user.setProfilePicUrl(awsS3Service.uploadFile(userDto.getImageProfile(), "profilePics", user.getUsername()));
                //userDao.save(user);
                System.out.println("Creato admin:\n" + user);
                return user;

            }
            System.out.println("sono null");
            return null;
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> getUserByHeader(String header) {
        try{
            GoogleIdToken idToken = GoogleTokenUtils.getTokenByHeader(header);
            if(idToken != null){
                String email = idToken.getPayload().getEmail();
                System.out.println("login no null");
                return userDao.findByEmail(email);
            }
            System.out.println("login null");
            return Optional.empty();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
