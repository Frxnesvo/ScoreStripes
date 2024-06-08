package com.example.clientuser.model

import android.graphics.Bitmap
import android.net.Uri
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.LeagueDto
import kotlinx.coroutines.runBlocking

class League(
    val id: String,
    val name: String,
    val image: Bitmap
){
    companion object{
        fun fromDto(leagueDto: LeagueDto): League{
            val image = runBlocking {
                S3ImageDownloader.getImageFromPresignedUrl(leagueDto.picUrl)
            } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

            return League(
                id = leagueDto.id,
                name = leagueDto.name,
                image = image
            )
        }
    }
}