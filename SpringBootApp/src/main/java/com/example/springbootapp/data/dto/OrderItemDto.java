package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private String id;
    private Integer quantity;
    private Double price;
    private Personalization personalization;
    private ProductWithVariantDto product;
}
