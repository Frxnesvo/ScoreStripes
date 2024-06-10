package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AddressCreateRequestDto (
    val street: String,
    val city: String,
    val civicNumber: String,
    val zipCode: String,
    val state: String,
    val defaultAddress: Boolean
)