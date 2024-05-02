package com.example.clientuser.model

data class Club(
    val id: String,
    val name: String,
    val picUrl: String,
    val league: League,
    val products: List<Product>
)