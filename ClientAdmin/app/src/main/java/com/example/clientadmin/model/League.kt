package com.example.clientadmin.model

class League(
    val id: String,
    val name: String,
    val picUrl: String,
    val clubs: List<Club>
){
    init {
        validateName(name)
    }
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 1..30
        }
    }
}