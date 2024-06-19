package com.example.clientuser.model.dto

import com.example.clientuser.model.Address
import com.example.clientuser.model.enumerator.Gender
import java.time.LocalDate

data class CustomerDto(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val favouriteTeam: String,
    val address: Address,
    val profilePicUrl: String
)