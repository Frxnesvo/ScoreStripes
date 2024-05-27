package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.dto.AdminDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface LoginApiService {
    @GET("/api/v1/auth/admin-login")
    fun checkAdminLogin(@Query("token") token: String): Call<AdminDto>

    @Multipart
    @POST("/api/v1/auth/admin-register")
    fun adminRegister(@Part("adminCreateRequestDto") adminCreateRequestDto: AdminCreateRequestDto): Call<AdminDto>

}