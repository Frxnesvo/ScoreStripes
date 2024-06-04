package com.example.clientuser.model.dto

import com.example.clientuser.model.Personalization
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AddToCartRequestDto(
    val productId: String,
    val size: Size,
    val category: ProductCategory,
    val personalization: Personalization
)