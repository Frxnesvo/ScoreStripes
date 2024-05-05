package com.example.clientadmin.model

import android.graphics.Bitmap

class League(
    val nameLeague: String,
    val imageLeague: Bitmap?
){
    init {
        if (!validateNameLeague(nameLeague))
            throw IllegalArgumentException("Name cannot be empty and greater than 20 characters")
    }

    companion object{
        fun validateNameLeague(nameLeague: String): Boolean{
            return nameLeague.length in 1..20
        }
    }
}