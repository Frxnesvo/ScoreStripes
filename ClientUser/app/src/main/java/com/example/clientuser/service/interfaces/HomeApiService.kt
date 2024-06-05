package com.example.clientuser.service.interfaces


import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.model.dto.LeagueDto
import retrofit2.Call
import retrofit2.http.GET

interface HomeApiService {
    @GET
    fun getClubs() : Call<List<ClubDto>>

    @GET
    fun getMostSelledProduct(){
        TODO("manca controller rest")
    }

    @GET
    fun getLeagues() : Call<List<LeagueDto>>
}