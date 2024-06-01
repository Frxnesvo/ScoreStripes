package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.dto.AdminDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {
    @GET("/api/v1/auth/admin-login")
    fun checkAdminLogin(@Header("Authorization") token: String): Call<AdminDto>

    @Multipart
    @POST("/api/v1/auth/admin-register")
    fun adminRegister(
        @Header("Authorization") token: String,
        @Part("userDto") userDto: AdminCreateRequestDto,
        //@Part imageProfile: MultipartBody.Part
    ): Call<AdminDto>
}