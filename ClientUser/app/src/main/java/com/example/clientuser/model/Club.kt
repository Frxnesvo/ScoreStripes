package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.dto.ClubDto

class Club(
    val id: String,
    val name: String,
    val image: Bitmap,
    val leagueName: String
){
    companion object{
        suspend fun fromDto(clubDto: ClubDto): Club{
            return Club(
                id = clubDto.id,
                name = clubDto.name,
                image = S3ImageDownloader.getImageFromBucket(clubDto.picUrl),
                leagueName = clubDto.leagueName
            )
        }
    }
}