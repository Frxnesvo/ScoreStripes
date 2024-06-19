package com.example.clientadmin.model.embdeddables

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Personalization(
    val playerName: String,
    val playerNumber: Int
)