package com.example.clientuser.model.dto

import com.example.clientuser.model.Personalization

data class CartItemDto(
    val id: String,
    val quantity: Int,
    val personalization: Personalization,
    val productWithVariantDto: ProductWithVariantDto,
    val price: Double
)