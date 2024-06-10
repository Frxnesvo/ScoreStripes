package com.example.springbootapp.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemUpdateDto {
    @Min(1)
    @Max(100)
    Integer quantity;
}
