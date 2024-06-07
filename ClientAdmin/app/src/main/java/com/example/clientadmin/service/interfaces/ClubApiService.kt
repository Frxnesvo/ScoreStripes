package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.ClubDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClubApiService {
    @Multipart
    @POST("/api/v1/clubs")
    fun createClub(
        @Part("name") name: String,
        @Part("leagueName") league: String,
        @Part pic: MultipartBody.Part,
    ): Call<ClubDto>

    @GET("/api/v1/clubs/names")
    fun getClubNames(): Call<List<String>>
}