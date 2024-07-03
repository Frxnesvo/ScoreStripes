package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.S3ImageDownloader
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class Club(
    val name: String,
    val league: String,
    val pic: Bitmap,
){
    fun toQueryString(): String {
        val picBase64 = ConverterBitmap.bitmapToBase64(pic)
        val encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString())
        val encodedLeague = URLEncoder.encode(league, StandardCharsets.UTF_8.toString())
        val encodedPic = URLEncoder.encode(picBase64, StandardCharsets.UTF_8.toString())
        return "name=$encodedName&league=$encodedLeague&pic=$encodedPic"
    }
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 3..40 && name.isNotBlank()
        }
        fun validateImage(image: Bitmap?): Boolean{
            return image != null
        }
        suspend fun fromDto(clubDto: ClubDto): Club{
            return Club(
                name = clubDto.name,
                pic = S3ImageDownloader.getImageFromBucket(clubDto.picUrl),
                league = clubDto.leagueName
            )
        }
        fun fromQueryString(queryString: String): Club {
            val params = queryString.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val name = params["name"] ?: throw IllegalArgumentException("Id is missing")
            val league = params["league"] ?: throw IllegalArgumentException("Username is missing")
            val picBase64 = params["pic"] ?: throw IllegalArgumentException("Pic is missing")
            val pic = ConverterBitmap.base64ToBitmap(picBase64)

            return Club(name, league, pic)
        }
    }
}