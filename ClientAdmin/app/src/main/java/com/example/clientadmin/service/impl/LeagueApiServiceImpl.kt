package com.example.clientadmin.service.impl

import android.content.Context
import android.net.Uri
import com.example.clientadmin.model.dto.LeagueDto
import com.example.clientadmin.model.dto.LeagueRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeagueApiServiceImpl {
    suspend fun createLeague(context: Context, name: String, pic: Uri): LeagueDto? {
        val multipart = ConverterUri.convert(context, pic, "image")
        if (multipart == null) {
            println("Errore nella conversione dell'Uri in MultipartBody.Part")
            return null
        }
        val leagueRequestDto = LeagueRequestDto(name, multipart)

        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitHandler.getLeagueApi().createLeague(leagueRequestDto)
                if (response.isSuccessful) response.body()
                else {
                    println("errore nella creazione della lega ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                println("eccezione nella creazione della lega")
                e.printStackTrace()
                null
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