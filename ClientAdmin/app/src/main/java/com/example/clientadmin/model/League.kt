package com.example.clientadmin.model

import android.net.Uri

class League(
    val name: String,
    val image: Uri
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(image)) { "Invalid image: cannot be empty" }
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