package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.model.dto.ClubRequestDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClubApiService {
    @Multipart
    @POST("/api/v1/clubs")
    fun createClub(@Part("clubRequestDto") clubRequestDto: ClubRequestDto): Call<ClubDto>

    @GET("/api/v1/clubs/names")
    fun getClubNames(): Call<List<String>>
}