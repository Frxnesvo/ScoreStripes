package com.example.clientadmin.model.dto

import com.example.clientadmin.model.embdeddables.Personalization
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderItemDto (
    val id: String,
    val quantity: Int,
    val price: Double,
    val personalization: Personalization,
    val product: ProductWithVariantDto
)