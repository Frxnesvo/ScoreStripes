package com.example.clientuser.model.dto

import com.example.clientuser.model.Personalization

data class CartItemDto(
    val id: String,
    val quantity: String,
    val personalization: Personalization,
    val productWithVariantDto: ProductWithVariantDto
)