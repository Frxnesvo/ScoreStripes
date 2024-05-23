package com.example.clientadmin.model

import android.content.Context
import android.net.Uri
import com.example.clientadmin.model.dto.LeagueRequestDto
import com.example.clientadmin.service.ConverterUri

class League(
    val name: String,
    val image: Uri
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(image)) { "Invalid image: cannot be empty" }
    }

    fun request(context: Context): LeagueRequestDto {
        try {
            val multipart = ConverterUri.convert(context, image, "pic")
            return LeagueRequestDto(name, multipart!!)
        }
        catch (e: Exception) { throw e }
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