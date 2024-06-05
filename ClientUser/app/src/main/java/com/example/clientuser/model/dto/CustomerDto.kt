package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.Gender

data class CustomerDto(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: Gender,
    val favouriteTeam: String
)