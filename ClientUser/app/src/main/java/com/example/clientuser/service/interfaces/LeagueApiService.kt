package com.example.clientuser.service.interfaces


import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.model.dto.LeagueDto
import retrofit2.Call
import retrofit2.http.GET

interface LeagueApiService {
    @GET("/api/v1/leagues")
    fun getLeagues() : Call<List<LeagueDto>>
}