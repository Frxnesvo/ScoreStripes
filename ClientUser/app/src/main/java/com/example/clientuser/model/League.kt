package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.dto.LeagueDto

class League(
    val id: String,
    val name: String,
    val image: Bitmap
){
    companion object{
        suspend fun fromDto(leagueDto: LeagueDto): League{

            return League(
                id = leagueDto.id,
                name = leagueDto.name,
                image = S3ImageDownloader.getImageFromBucket(leagueDto.picUrl)
            )
        }
    }
}