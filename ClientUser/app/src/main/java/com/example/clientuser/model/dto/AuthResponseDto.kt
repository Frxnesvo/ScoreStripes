package com.example.clientuser.model.dto

import com.example.clientuser.model.Address
import com.example.clientuser.model.enumerator.Gender
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class AuthResponseDto(
    val jwt: String,
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val email: String,
    val gender: Gender,
    val profilePicUrl: String,
    val favouriteTeam: String,
    val address: Address
): Serializable
