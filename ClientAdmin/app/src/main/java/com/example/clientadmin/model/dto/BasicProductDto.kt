package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasicProductDto(
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val gender: Gender,
    val picUrl: String,
    val club: String,
    val league: String
)
