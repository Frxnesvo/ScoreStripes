package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import com.example.springbootapp.security.customValidators.ValidClubName;
import com.example.springbootapp.security.customValidators.ValidImageExtension;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CustomerRegisterDto {

    @NotBlank
    private String idToken;

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9_]+$", message = "Name must contain at least one lowercase letter, one uppercase letter, and can only contain letters, numbers, and underscores")
    private String username;

    @Past
    private LocalDate birthDate;

    @ValidImageExtension            //custom validator
    private MultipartFile profilePic;

    @NotNull
    private Gender gender;

    @NotNull
    @Size(min = 3, max = 40)
    @ValidClubName                  //custom validator
    private String favoriteTeam;

    @NotNull
    private AddressRequestDto address;
}
