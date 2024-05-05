package com.example.clientadmin.model

import android.graphics.Bitmap

class Team(
    nameTeam: String,
    imageTeam: Bitmap?,
    league: String
) {
    init {
        if (!validateNameTeam(nameTeam))
            throw IllegalArgumentException("Name cannot be empty and grater than 30 characters")

        if (!validateNameLeague(league))
            throw IllegalArgumentException("This league not exist")
    }

    companion object{
        fun validateNameTeam(nameTeam: String): Boolean{
            return nameTeam.length in 1..30
        }
        fun validateNameLeague(nameLeague: String): Boolean{
            //TODO
            return true
        }
    }
}