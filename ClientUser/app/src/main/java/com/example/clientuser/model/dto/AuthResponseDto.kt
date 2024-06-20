package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.Gender
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class AuthResponseDto(
    val jwt: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val email: String,
    val gender: Gender,
    val profilePicUrl: String
)
