package com.example.clientadmin.model.dto

import okhttp3.MultipartBody

data class ClubRequestDto(
    val name: String,

    val pic: MultipartBody.Part
)
