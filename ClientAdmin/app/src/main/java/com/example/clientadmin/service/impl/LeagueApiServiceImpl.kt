package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.LeagueDto
import com.example.clientadmin.model.dto.LeagueRequestDto
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.LeagueApiService
import retrofit2.Call

class LeagueApiServiceImpl: LeagueApiService {
    override fun createLeague(leagueRequestDto: LeagueRequestDto): Call<LeagueDto> {
        return RetrofitHandler.leagueApi.createLeague(leagueRequestDto)
    }
    override fun getLeagueNames(): Call<List<String>> {
        return RetrofitHandler.leagueApi.getLeagueNames()
    }
}