package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class AdminDto (
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val profilePicUrl: String,
    val birthDate: LocalDate,
)