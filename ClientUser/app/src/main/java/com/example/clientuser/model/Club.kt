package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.ClubDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class Club(
    val id: String,
    val name: String,
    val image: Bitmap
){
    companion object{
        suspend fun fromDto(clubDto: ClubDto): Club{
            return Club(
                id = clubDto.id,
                name = clubDto.name,
                image = S3ImageDownloader.download(clubDto.picUrl).first()
            )
        }
    }
}