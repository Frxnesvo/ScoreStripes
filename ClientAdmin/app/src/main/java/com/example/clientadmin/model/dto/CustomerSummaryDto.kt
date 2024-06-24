package com.example.clientadmin.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomerSummaryDto (
    val id: String,
    val username: String,
    val profilePicUrl: String
)