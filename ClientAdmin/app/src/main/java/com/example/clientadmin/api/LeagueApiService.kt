package com.example.clientadmin.api

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
        @Part("name") name: String,
        @Part pic: MultipartBody.Part
    ): Call<LeagueDto>

    @GET("/api/v1/leagues")
    fun getLeagues() : Call<List<LeagueDto>>

//    @GET("/api/v1/leagues/names")
//    fun getLeagueNames(): Call<List<String>>
}