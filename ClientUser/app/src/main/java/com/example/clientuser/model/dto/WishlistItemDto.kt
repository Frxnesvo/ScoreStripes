package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class WishlistItemDto(
    val id: String,
    val dateAdded: LocalDate,
    val product: ProductDto
)
