package com.example.clientadmin.model

import android.net.Uri

class Club(
    val name: String,
    val image: Uri,
    val league: String
){
    init {
        validateName(name)
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