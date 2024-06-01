package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.Gender
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomerProfileDto (
    val id : String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: Gender,
    val favoriteClub: String,
)