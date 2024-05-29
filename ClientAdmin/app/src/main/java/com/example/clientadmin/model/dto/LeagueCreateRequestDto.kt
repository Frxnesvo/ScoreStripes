package com.example.clientadmin.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueCreateRequestDto (
    val name: String
)