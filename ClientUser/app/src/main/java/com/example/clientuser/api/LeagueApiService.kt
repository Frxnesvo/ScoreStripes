package com.example.clientuser.api


import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.model.dto.LeagueDto
import retrofit2.Call
import retrofit2.http.GET

interface LeagueApiService {
    @GET("/api/v1/leagues")
    fun getLeagues() : Call<List<LeagueDto>>

    @GET("/api/v1/leagues/names")
    fun getLeaguesNames() : Call<List<String>>
}