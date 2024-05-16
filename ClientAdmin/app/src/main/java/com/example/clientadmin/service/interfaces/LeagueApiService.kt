package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.model.dto.LeagueDto
import com.example.clientadmin.model.dto.LeagueRequestDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface LeagueApiService {
    @POST("/api/v1/leagues")
    fun createLeague(@Part("leagueRequestDto") leagueRequestDto: LeagueRequestDto): Response<LeagueDto>

    @GET("/api/v1/leagues/names")
    fun getLeagueNames(): Response<List<String>>
}