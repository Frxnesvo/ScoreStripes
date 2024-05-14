package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerProfileDto {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private String favouriteTeam;
}
