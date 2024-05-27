package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import java.time.LocalDate

data class AdminDto (
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val picUrl: String,
    val birthDate: LocalDate,
)