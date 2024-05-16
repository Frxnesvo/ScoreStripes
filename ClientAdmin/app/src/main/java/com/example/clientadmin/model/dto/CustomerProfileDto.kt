package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender

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