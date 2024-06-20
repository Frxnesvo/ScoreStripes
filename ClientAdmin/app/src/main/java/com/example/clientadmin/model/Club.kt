package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.utils.S3ImageDownloader

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
            return name.length in 3..40 && name.isNotBlank()
        }
        fun validateImage(image: Bitmap): Boolean{
            return image != Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
        fun fromDto(clubDto: ClubDto): Club{
            return Club(
                name = clubDto.name,
                image = S3ImageDownloader.getImageForBucket(clubDto.picUrl),
                league = "clubDto.league" //todo non so perchè non mi viene mandata anche la lega
            )
        }
    }
}