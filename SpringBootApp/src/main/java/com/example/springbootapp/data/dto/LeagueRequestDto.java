package com.example.springbootapp.data.dto;

import com.example.springbootapp.security.customValidators.ValidImageExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class LeagueRequestDto {
    @NotBlank
    @Size(min = 3, max = 25)
    private String name;
    @NotNull
    @ValidImageExtension
    MultipartFile pic;
}
