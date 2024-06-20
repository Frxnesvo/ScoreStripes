package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.S3ImageDownloader

class Club(
    val name: String,
    val league: String,
    val pic: Bitmap,
){
    init {
        require(validateName(name)) { "Invalid name: must be between 3 and 40 characters" }
        require(validateImage(pic)) { "Invalid pic: cannot be empty" }
    }
    fun toQueryString(): String {
        val picBase64 = ConverterBitmap.bitmapToBase64(pic)
        return "name=$name&league=$league&pic=$picBase64"
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
                pic = S3ImageDownloader.getImageForBucket(clubDto.picUrl),
                league = "clubDto.league" //todo non so perchè non mi viene mandata anche la lega
            )
        }
        fun fromQueryString(queryString: String): Club {
            val params = queryString.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val name = params["id"] ?: throw IllegalArgumentException("Id is missing")
            val league = params["username"] ?: throw IllegalArgumentException("Username is missing")
            val picBase64 = params["pic"] ?: throw IllegalArgumentException("Pic is missing")
            val pic = ConverterBitmap.base64ToBitmap(picBase64)

            return Club(name, league, pic)
        }
    }
}