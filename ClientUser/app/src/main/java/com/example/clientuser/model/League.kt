package com.example.clientuser.model

data class League(
    val id: String,
    val name: String,
    val picUrl: String,
    val clubs: List<Club>
)