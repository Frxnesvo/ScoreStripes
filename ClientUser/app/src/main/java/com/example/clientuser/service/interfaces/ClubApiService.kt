package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.ClubDto
import retrofit2.Call
import retrofit2.http.GET

interface ClubApiService {
    @GET("/api/v1/clubs/names")
    fun getClubsName() : Call<List<String>>
}