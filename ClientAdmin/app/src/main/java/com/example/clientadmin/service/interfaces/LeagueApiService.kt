package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.LeagueCreateRequestDto
import com.example.clientadmin.model.dto.LeagueDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LeagueApiService {
    @Multipart
    @POST("/api/v1/leagues")
    fun createLeague(
        @Part("leagueCreateRequestDto") leagueCreateRequestDto: LeagueCreateRequestDto,
        @Part pic: MultipartBody.Part
    ): Call<LeagueDto>

    @GET("/api/v1/leagues/names")
    fun getLeagueNames(): Call<List<String>>
}