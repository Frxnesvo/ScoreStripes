package com.example.clientadmin.model.dto

import com.example.clientadmin.model.Personalization


data class OrderItemDto (
    val id: String,
    val quantity: Int,
    val price: Double,
    val personalization: Personalization,
    val product: ProductWithVariantDto
)