package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.DTO.ClubDto
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClubApiService {
    @POST("/api/v1/clubs")
    @Multipart
    fun createClub(@Part("clubRequestDto") clubRequestDto: RequestBody): Call<ClubDto>

    @GET("/api/v1/clubs/names")
    fun getClubNames(): Call<List<String>>
}