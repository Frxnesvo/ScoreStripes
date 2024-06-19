package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AuthResponseDto {

    private String jwt;
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String profilePicUrl;
    private String email;
    private LocalDate birthDate;
    private Gender gender;
}
