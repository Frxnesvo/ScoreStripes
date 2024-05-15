package com.example.clientadmin.service.impl

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.LeagueRequestDto
import com.example.clientadmin.service.BitmapConverter
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeagueApiServiceImpl {
    suspend fun createLeague(name: String, pic: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val leagueRequestDto = LeagueRequestDto(name, BitmapConverter.bitmapToMultipartBodyPart(pic))
                val response = RetrofitHandler.getLeagueApi().createLeague(leagueRequestDto)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun getLeagueNames(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitHandler.getLeagueApi().getLeagueNames()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}