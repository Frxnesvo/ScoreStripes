package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.UserDao;
import com.example.springbootapp.data.entities.User;
import com.example.springbootapp.security.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userDao.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return createUserDetails(user);
    }

    public UserDetails loadUserByEmail(String email){
        User user = userDao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return createUserDetails(user);
    }

    public UserDetails loadUserById(String id){
        User user= userDao.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return createUserDetails(user);
    }


    private UserDetails createUserDetails(User user) {
        return new CustomUserDetails(user);
    }

    public User getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        return userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public String getCurrentUserId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getID();
    }

}
