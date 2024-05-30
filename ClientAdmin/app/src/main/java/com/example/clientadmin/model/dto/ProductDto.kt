package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val gender: Gender,
    val productCategory: ProductCategory,
    val pics: List<String>,
    val clubName: String,
    val variants: List<ProductWithVariantAvailabilityDto>
)