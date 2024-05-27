package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import okhttp3.MultipartBody
import java.time.LocalDate

data class AdminCreateRequestDto (
    val username: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val pic: MultipartBody.Part,
    val gender: Gender
)