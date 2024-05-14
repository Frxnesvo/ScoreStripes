package com.example.clientadmin.model

data class Address(
    val id: String,
    val street: String,
    val city: String,
    val civicNumber: String,
    val zipCode: String,
    val state: String,
    val defaultAddress: Boolean,
    val customer: Customer
    )