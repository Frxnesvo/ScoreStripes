package com.example.clientadmin.model.embdeddables

data class OrderInformations (
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val civicNumber: String,
    val customerEmail: String,
    val customerFirstName: String,
    val customerLastName: String
)