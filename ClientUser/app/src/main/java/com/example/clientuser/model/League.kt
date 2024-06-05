package com.example.clientuser.model

import android.net.Uri
import com.example.clientuser.model.dto.LeagueDto

class League(
    val id: String,
    val name: String,
    val image: Uri
){
    companion object{
        fun fromDto(leagueDto: LeagueDto): League{
            return League(
                id = leagueDto.id,
                name = leagueDto.name,
                image = Uri.EMPTY //TODO prendere dal bucket
            )
        }
    }
}