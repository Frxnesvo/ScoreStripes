package com.example.clientuser.api

import com.example.clientuser.model.dto.AuthResponseDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {
    @POST("/api/v1/auth/login")
    fun login(@Body idToken: Map<String, String>): Call<AuthResponseDto> //TODO prendere il dto dell'utente

    @Multipart
    @POST("/api/v1/auth/register-customer")
    fun customerRegister(
        @Part idToken: MultipartBody.Part,
        @Part username: MultipartBody.Part,
        @Part birthDate: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part imageProfile: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part favouriteTeam: MultipartBody.Part
        //TODO fare l'aggiunta dell'address
    ): Call<Map<String,String>>
}