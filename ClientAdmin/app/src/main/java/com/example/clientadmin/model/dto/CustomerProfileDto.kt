package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
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
    val favouriteTeam: String,
)