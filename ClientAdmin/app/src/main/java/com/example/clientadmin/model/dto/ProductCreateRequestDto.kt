package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductCreateRequestDto (
    val name: String,
    val description: String,
    val price: Double,
    val club: String,
    val brand: String,
    val gender: Gender,
    val category: ProductCategory,
    val variants: Map<Size, Int>
)