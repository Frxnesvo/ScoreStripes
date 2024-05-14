package com.example.springbootapp.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ClubRequestDto {
    @NotBlank
    @Size(min = 3, max = 40)
    private String name;
    @NotNull
    MultipartFile pic;
    @NotBlank
    @Size(min = 3, max = 40)
    private String leagueName;
}
