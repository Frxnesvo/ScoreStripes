package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueDto (
    val id: String,
    val name: String,
    val picUrl: String
)