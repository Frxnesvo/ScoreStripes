package com.example.springbootapp.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddToWishlistRequestDto {
    @NotBlank
    private String productId;
}
