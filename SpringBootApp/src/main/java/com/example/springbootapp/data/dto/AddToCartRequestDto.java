package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import com.example.springbootapp.data.entities.Enums.Size;
import com.example.springbootapp.security.customValidators.ValidPersonalization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ValidPersonalization   // Annotazione custom
public class AddToCartRequestDto {
    @NotBlank
    private String productId;
    @NotNull
    private Size size;
    @NotNull
    private ProductCategory category;
    private Personalization personalization;
}
