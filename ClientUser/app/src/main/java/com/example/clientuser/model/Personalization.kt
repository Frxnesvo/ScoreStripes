package com.example.clientuser.model

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
class Personalization(
    val playerName: String?,
    val playerNumber: Int?
)