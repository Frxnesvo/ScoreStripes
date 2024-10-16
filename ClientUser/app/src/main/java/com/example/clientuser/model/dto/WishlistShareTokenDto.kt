package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WishlistShareTokenDto(
    val id: String,
    val token: String
)
