package com.example.clientadmin.model

import com.example.clientadmin.model.enumerator.Gender
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