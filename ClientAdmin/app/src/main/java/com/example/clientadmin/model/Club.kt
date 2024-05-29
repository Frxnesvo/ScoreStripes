package com.example.clientadmin.model

import android.net.Uri

class Club(
    val name: String,
    val image: Uri,
    private val league: String
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(image)) { "Invalid image: cannot be empty" }
    }
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 3..40
        }
        fun validateImage(image: Uri): Boolean{
            return image != Uri.EMPTY
        }
    }
}