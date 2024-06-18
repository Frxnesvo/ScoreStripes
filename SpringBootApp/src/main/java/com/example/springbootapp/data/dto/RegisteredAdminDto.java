package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegisteredAdminDto {
    String id;
    String username;
    String firstName;
    String lastName;
    String email;
    Gender gender;
    String profilePicUrl;
    LocalDate birthDate;
}