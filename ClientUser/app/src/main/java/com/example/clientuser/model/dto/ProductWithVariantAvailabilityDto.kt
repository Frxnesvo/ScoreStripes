package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductWithVariantAvailabilityDto(
    val id: String,
    val size: Size,
    val availability: Int
)
