package com.example.clientadmin.model

data class AddressEmbeddable(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val civicNumber: String
)