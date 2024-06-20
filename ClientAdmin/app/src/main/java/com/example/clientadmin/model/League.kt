package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.LeagueDto
import com.example.clientadmin.utils.S3ImageDownloader

class League(
    val name: String,
    val image: Bitmap
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(image)) { "Invalid image: cannot be empty" }
    }

    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 3..25 && name.isNotBlank()
        }
        fun validateImage(image: Bitmap): Boolean{
            return image != Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
        fun fromDto(leagueDto: LeagueDto): League{

            return League(
                name = leagueDto.name,
                image = S3ImageDownloader.getImageForBucket(leagueDto.picUrl)
            )
        }
    }
}