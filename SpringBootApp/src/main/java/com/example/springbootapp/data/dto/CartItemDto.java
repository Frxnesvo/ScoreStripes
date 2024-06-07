package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;

public class CartItemDto {
    private String id;
    private String quantity;
    private Personalization personalization;
    private ProductWithVariantDto productWithVariant;
}
