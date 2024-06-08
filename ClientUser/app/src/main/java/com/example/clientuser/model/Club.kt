package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.ClubDto
import kotlinx.coroutines.runBlocking

class Club(
    val id: String,
    val name: String,
    val image: Bitmap
){
    companion object{
        fun fromDto(clubDto: ClubDto): Club{
            val image = runBlocking {
                S3ImageDownloader.getImageFromPresignedUrl(clubDto.picUrl)
            } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

            return Club(
                id = clubDto.id,
                name = clubDto.name,
                image = image
            )
        }
    }
}