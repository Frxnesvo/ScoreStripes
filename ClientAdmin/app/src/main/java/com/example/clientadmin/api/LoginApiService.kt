package com.example.clientadmin.api

import com.example.clientadmin.model.dto.AdminDto
import com.example.clientadmin.model.enumerator.Gender
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.time.LocalDate

interface LoginApiService {
    @GET("/api/v1/auth/admin-login")
    fun checkAdminLogin(@Header("Authorization") token: String): Call<AdminDto>

    @Multipart
    @POST("/api/v1/auth/admin-register")
    fun adminRegister(
        @Header("Authorization") token: String,
        @Part("username") username: String,
        @Part("birthDate") birthDate: LocalDate,
        @Part("gender") gender: Gender,
        @Part imageProfile: MultipartBody.Part
    ): Call<AdminDto>
}