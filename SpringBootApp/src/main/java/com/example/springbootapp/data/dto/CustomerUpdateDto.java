package com.example.springbootapp.data.dto;

import com.example.springbootapp.security.customValidators.ValidClubName;
import com.example.springbootapp.security.customValidators.ValidImageExtension;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class CustomerUpdateDto {

    @Size(min = 3, max = 40)
    @ValidClubName                      //custom validator
    private String favoriteTeam;

    @ValidImageExtension                //custom validator
    private MultipartFile profilePic;
}

