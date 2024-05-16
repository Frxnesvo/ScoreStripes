package com.example.clientadmin.model.dto

data class AddressDto (
    val id: String,
    val street: String,
    val city: String,
    val civicNumber: String,
    val zipCode: String,
    val state: String,
    val defaultAddress: Boolean
)