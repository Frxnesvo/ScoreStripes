package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.model.enumerator.ProductCategory
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val gender: Gender,
    val category: ProductCategory,
    val pics: List<ProductPicDto>,
    val clubName: String,
    val variants: List<ProductWithVariantAvailabilityDto>
)