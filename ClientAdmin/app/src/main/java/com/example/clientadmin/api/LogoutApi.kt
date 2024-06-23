package com.example.clientadmin.api

import retrofit2.Call
import retrofit2.http.POST

interface LogoutApi {

    @POST("/api/v1/auth/logout")
    fun logout(): Call<String>

    //TODO da fare nel backend e controllare
}