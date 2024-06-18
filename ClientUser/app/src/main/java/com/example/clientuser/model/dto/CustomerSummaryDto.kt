package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CustomerSummaryDto(
    val id: String,
    val profilePic: String,
    val username: String
)