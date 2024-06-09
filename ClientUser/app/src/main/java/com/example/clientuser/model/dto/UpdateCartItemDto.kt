package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateCartItemDto(
    val id: String,
    val quantity: Int
)