package com.example.springbootapp.Data.DTO;

import com.example.springbootapp.Data.Entities.Embdeddables.Personalization;
import com.example.springbootapp.Data.Entities.ProductWithVariant;
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
