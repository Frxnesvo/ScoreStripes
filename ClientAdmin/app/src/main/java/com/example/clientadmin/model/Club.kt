package com.example.clientadmin.model

import android.graphics.Bitmap

class Club(
    val name: String,
    val league: String,
    val image: Bitmap,
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(image)) { "Invalid image: cannot be empty" }
    }
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 3..40
        }
        fun validateImage(image: Bitmap): Boolean{
            return image != Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
    }
}