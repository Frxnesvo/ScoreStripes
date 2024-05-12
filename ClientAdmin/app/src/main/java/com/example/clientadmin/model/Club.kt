package com.example.clientadmin.model

class Club(
    val id: String,
    val name: String,
    val picUrl: String,
    val league: League,
    val products: List<Product>
){
    init {
        validateName(name)
    }
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 3..40
        }
    }
}