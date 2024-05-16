package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Size

data class ProductWithVariantDto(
    val id: String,
    val size: Size,
    val product: ProductDto
)