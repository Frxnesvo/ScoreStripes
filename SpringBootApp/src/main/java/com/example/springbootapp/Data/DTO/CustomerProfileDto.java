package com.example.springbootapp.Data.DTO;

import com.example.springbootapp.Data.Entities.Address;
import com.example.springbootapp.Data.Entities.Enums.Gender;
import com.example.springbootapp.Data.Entities.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
