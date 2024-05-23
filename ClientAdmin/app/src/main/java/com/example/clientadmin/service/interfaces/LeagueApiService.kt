package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.LeagueDto
import com.example.clientadmin.model.dto.LeagueRequestDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LeagueApiService {
    @Multipart
    @POST("/api/v1/leagues")
    fun createLeague(@Part("leagueRequestDto") leagueRequestDto: LeagueRequestDto): Call<LeagueDto>

    @GET("/api/v1/leagues/names")
    fun getLeagueNames(): Call<List<String>>
}