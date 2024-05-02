package com.example.clientuser.model

import com.example.clientuser.model.Enum.Gender
import java.time.LocalDate

open class User(
    val id: String,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: LocalDate,
    val profilePicUrl: String,
    val gender: Gender
)