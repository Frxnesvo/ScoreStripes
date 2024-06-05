package com.example.clientuser.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AddressCreateRequestDto (
    var street: String,
    var city: String,
    var civicNumber: String,
    var zipCode: String,
    var state: String
)