package com.example.clientadmin.service.impl

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.service.BitmapConverter
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.ClubApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ClubApiServiceImpl{

    suspend fun getClubSNames(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitHandler.getClubApi().getClubNames()
                if (response.isSuccessful) response.body() ?: emptyList()
                else emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun createClub(name: String, pic: Bitmap): ClubDto?{
        val clubRequestDto = ClubRequestDto(name, BitmapConverter.bitmapToMultipartBodyPart(pic))

        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitHandler.getClubApi().createClub(clubRequestDto)
                if(response.isSuccessful) response.body()
                else{
                    println("errore nella creazione del club ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                println("eccezione nella creazione del club")
                e.printStackTrace()
                null
            }
        }
    }

}