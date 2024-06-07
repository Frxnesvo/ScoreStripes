package com.example.clientuser.model.dto

import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.enumerator.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductWithVariantDto(
    val id: String,
    val size: Size,
    val product: BasicProductDto
)
