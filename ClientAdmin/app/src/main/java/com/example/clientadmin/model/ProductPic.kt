package com.example.clientadmin.model

data class ProductPic(
    val id: String,
    val picUrl: String,
    val principal: Boolean,
    val product: Product
)