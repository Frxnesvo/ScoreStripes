package com.example.clientadmin.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClubCreateRequestDto(
    val name: String,
    val league: String
)
