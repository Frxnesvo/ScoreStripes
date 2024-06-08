package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.Gender
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasicProductDto(
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val gender: Gender,
    val picUrl: String,
    val club: String
)