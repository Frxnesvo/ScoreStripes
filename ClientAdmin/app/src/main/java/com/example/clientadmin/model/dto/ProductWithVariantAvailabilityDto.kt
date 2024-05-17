package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Size

data class ProductWithVariantAvailabilityDto(
    val id: String,
    val size: Size,
    val availability: Int
)
