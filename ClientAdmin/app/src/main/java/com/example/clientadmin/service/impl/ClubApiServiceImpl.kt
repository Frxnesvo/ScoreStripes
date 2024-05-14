package com.example.clientadmin.service.impl

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.service.BitmapConverter
import com.example.clientadmin.service.interfaces.ClubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ClubApiServiceImpl{
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val clubApi = retrofit.create(ClubApiService::class.java)

    suspend fun getClubSNames(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = clubApi.getClubNames()
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

    suspend fun createClub(name: String, pic: Bitmap): Boolean{
        val clubRequestDto = ClubRequestDto(name, BitmapConverter.bitmapToMultipartBodyPart(pic))

        return withContext(Dispatchers.IO) {
            try {
                val response = clubApi.createClub(clubRequestDto)
                response.isExecuted
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }


}