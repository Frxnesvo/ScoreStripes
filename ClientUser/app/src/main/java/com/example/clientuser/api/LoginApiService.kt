package com.example.clientuser.api

import com.example.clientuser.model.dto.AuthResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {
    @POST("/api/v1/auth/login/customer")
    fun login(@Body idToken: Map<String, String>): Call<AuthResponseDto> //TODO prendere il dto dell'utente

    @Multipart
    @POST("/api/v1/auth/register-customer")
    fun customerRegister(
        @Part("idToken") idToken: RequestBody,
        @Part("username") username: RequestBody,
        @Part("birthDate") birthDate: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part imageProfile: MultipartBody.Part,
        @Part("favoriteTeam") favoriteTeam: RequestBody,

        @Part("address.state") state: RequestBody,
        @Part("address.city") city: RequestBody,
        @Part("address.street") street: RequestBody,
        @Part("address.zipCode") zipCode: RequestBody,
        @Part("address.civicNumber") civicNumber: RequestBody,
        @Part("address.defaultAddress") defaultAddress: RequestBody
    ): Call<Map<String,String>>
}