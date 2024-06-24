package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class AuthResponseDto {
    protected String jwt;
    protected String id;
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String profilePicUrl;
    protected String email;
    protected LocalDate birthDate;
    protected Gender gender;
}
