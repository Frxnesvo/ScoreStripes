package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import com.example.springbootapp.data.entities.Enums.Role;
import com.example.springbootapp.security.customValidators.ValidImageExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AdminCreateDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    @ValidImageExtension
    private MultipartFile imageProfile;

    @NotNull
    private Gender gender;

    @NotNull
    private Role role;
}
