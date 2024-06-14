package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductUpdateRequestDto(
    val description: String,
    val price: Double,
    val variants: Map<Size, Int>
)
