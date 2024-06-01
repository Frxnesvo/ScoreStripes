package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto (
    val id: String,
    val street: String,
    val city: String,
    val civicNumber: String,
    val zipCode: String,
    val state: String,
    val defaultAddress: Boolean
)