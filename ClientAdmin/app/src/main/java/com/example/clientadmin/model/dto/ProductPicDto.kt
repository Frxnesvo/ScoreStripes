package com.example.clientadmin.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductPicDto (
    val id: String,
    val picUrl: String,
    val principal: Boolean
)