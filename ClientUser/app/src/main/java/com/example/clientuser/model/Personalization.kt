package com.example.clientuser.model

import com.squareup.moshi.JsonClass

//TODO fare jsonClass
@JsonClass(generateAdapter = true)
class Personalization(
    val playerName: String?,
    val playerNumber: Int?
)