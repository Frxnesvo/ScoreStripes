package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class AdminCreateRequestDto (
    val idToken: String,
    val username: String,
    val birthDate: LocalDate,
    val gender: Gender,
)