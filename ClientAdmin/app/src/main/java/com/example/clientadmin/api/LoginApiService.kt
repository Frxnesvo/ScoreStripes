package com.example.clientadmin.api

import com.example.clientadmin.model.dto.AdminDto
import com.example.clientadmin.model.dto.AuthResponseDto
import com.example.clientadmin.model.enumerator.Gender
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.time.LocalDate

interface LoginApiService {

    @POST("/api/v1/auth/login")
    fun login(@Body idToken: Map<String, String>): Call<AuthResponseDto>

    @Multipart
    @POST("/api/v1/auth/register-admin")
    fun adminRegister(
        @Part idToken: MultipartBody.Part,
        @Part username: MultipartBody.Part,
        @Part birthDate: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part imageProfile: MultipartBody.Part
    ): Call<String>
}