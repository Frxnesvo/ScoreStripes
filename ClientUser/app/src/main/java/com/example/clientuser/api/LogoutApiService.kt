package com.example.clientuser.api

import retrofit2.Call
import retrofit2.http.POST

interface LogoutApiService {
    @POST("/api/v1/auth/logout")
    fun logout(): Call<Map<String, String>>
}