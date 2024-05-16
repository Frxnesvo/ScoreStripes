package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender

data class ProductDto (
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val gender: Gender,
    val picUrl: String,
    val club: String
)