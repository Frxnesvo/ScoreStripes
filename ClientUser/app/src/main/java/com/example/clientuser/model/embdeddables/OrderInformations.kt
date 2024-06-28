package com.example.clientuser.model.embdeddables

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
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