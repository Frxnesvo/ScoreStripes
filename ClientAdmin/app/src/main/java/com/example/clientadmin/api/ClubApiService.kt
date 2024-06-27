package com.example.clientadmin.api

import com.example.clientadmin.model.dto.ClubDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ClubApiService {
    @Multipart
    @POST("/api/v1/clubs")
    fun createClub(
        @Part name: MultipartBody.Part,
        @Part league: MultipartBody.Part,
        @Part pic: MultipartBody.Part,
    ): Call<ClubDto>

    @GET("/api/v1/clubs")
    fun getClubs(): Call<List<ClubDto>>

    @Multipart
    @PATCH("/api/v1/clubs/{name}")
    fun updateClub(
        @Path("name") name: String,
        @Part league: MultipartBody.Part,
        @Part pic: MultipartBody.Part,
    ): Call<ClubDto>
}