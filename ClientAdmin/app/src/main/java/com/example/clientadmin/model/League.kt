package com.example.clientadmin.model

import android.net.Uri

class League(
    val name: String,
    val image: Uri
){
    init {
        validateName(name)
        validateImage(image)
    }
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 1..30
        }

        fun validateImage(image: Uri): Boolean{
            return image != Uri.EMPTY
        }
    }
}