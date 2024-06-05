package com.example.clientuser.model

import android.net.Uri
import com.example.clientuser.model.dto.ClubDto

class Club(
    val id: String,
    val name: String,
    val image: Uri
){
    companion object{
        fun fromDto(clubDto: ClubDto): Club{
            return Club(
                id = clubDto.id,
                name = clubDto.name,
                image = Uri.EMPTY, //TODO prendere dal bucket
            )
        }
    }
}