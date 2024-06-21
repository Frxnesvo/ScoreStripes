package com.example.clientuser.model.dto

data class AddressRequestDto(
    val street: String,
    val city: String,
    val civicNumber: String,
    val zipCode: String,
    val state: String,
    val defaultAddress: Boolean
)