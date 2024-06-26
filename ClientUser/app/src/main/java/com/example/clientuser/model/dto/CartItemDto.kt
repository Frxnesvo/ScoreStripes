package com.example.clientuser.model.dto

import com.example.clientuser.model.Personalization
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartItemDto(
    val id: String,
    val quantity: Int,
    val personalization: Personalization?,
    val product: ProductWithVariantDto,
    val price: Double
)