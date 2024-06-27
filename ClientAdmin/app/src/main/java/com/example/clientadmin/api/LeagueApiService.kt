package com.example.clientadmin.api

import com.example.clientadmin.model.dto.LeagueDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface LeagueApiService {
    @Multipart
    @POST("/api/v1/leagues")
    fun createLeague(
        @Part name: MultipartBody.Part,
        @Part pic: MultipartBody.Part
    ): Call<LeagueDto>

    @GET("/api/v1/leagues")
    fun getLeagues() : Call<List<LeagueDto>>

    @Multipart
    @PATCH("/api/v1/leagues/{name}")
    fun updateLeague(
        @Path("name") name: String,
        @Part pic: MultipartBody.Part
    ): Call<LeagueDto>
}