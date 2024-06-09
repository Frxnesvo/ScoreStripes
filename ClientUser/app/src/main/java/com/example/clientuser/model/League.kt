package com.example.clientuser.model

import android.graphics.Bitmap
import android.net.Uri
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.LeagueDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

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
                image = S3ImageDownloader.download(leagueDto.picUrl).first()
            )
        }
    }
}