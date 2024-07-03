package com.example.clientadmin.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClubDto (
    val id: String,
    val name: String,
    val picUrl: String,
    val leagueName: String
)