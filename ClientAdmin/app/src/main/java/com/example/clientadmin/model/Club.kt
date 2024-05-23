package com.example.clientadmin.model

import android.content.Context
import android.net.Uri
import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.service.ConverterUri

class Club(
    val name: String,
    val image: Uri,
    private val league: String
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(image)) { "Invalid image: cannot be empty" }
    }

    fun request(context: Context): ClubRequestDto {
        try {
            val multipart = ConverterUri.convert(context, image, "pic")
            return ClubRequestDto(name, multipart!!, league)
        }
        catch (e: Exception) { throw e }
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