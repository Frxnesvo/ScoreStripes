package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {
    private String id;
    private Integer quantity;
    private Personalization personalization;
    private ProductWithVariantDto product;
    private Double price;
}
