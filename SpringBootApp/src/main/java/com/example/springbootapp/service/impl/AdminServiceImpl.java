package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.dto.AdminCreateDto;
import com.example.springbootapp.data.entities.Admin;
import com.example.springbootapp.data.entities.Enums.Role;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.service.interfaces.AdminService;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
    public User createAdmin(OAuth2User oauth2User, AdminCreateDto userDto) {
        User user = new Admin();
        user.setEmail(oauth2User.getAttributes().get("email").toString());
        user.setFirstName(oauth2User.getAttributes().get("given_name").toString());
        user.setLastName(oauth2User.getAttributes().get("family_name").toString());
        user.setRole(Role.ADMIN);
        user.setBirthDate(userDto.getBirthDate());
        user.setGender(userDto.getGender());
        user.setUsername(userDto.getUsername());
        user.setProfilePicUrl(awsS3Service.uploadFile(userDto.getImageProfile(), "profilePics", user.getUsername()));
        userDao.save(user);
        return user;
    }
}
