package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.dto.AdminDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {
    @GET("/api/v1/auth/admin-login")
    fun checkAdminLogin(@Header("Authorization") token: String): Call<AdminDto>


    //@Headers("Content-Type: application/json")
    @Multipart
    @POST("/api/v1/auth/admin-register")
    fun adminRegister(@Header("Authorization") @Part adminCreateRequestDto: AdminCreateRequestDto): Call<AdminDto>

}