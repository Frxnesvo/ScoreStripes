package com.example.clientadmin.model.dto

import okhttp3.MultipartBody

data class LeagueRequestDto (
    val name: String,
    val pic: MultipartBody.Part
)