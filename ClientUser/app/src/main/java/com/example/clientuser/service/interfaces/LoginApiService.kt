package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.CustomerCreateRequestDto
import com.example.clientuser.model.dto.CustomerDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {
    @GET("/api/v1/auth/customer-login")
    fun checkCustomerLogin(@Header("Authorization") token : String) : Call<CustomerDto>

    @Multipart
    @POST("/api/v1/auth/customer-register")
    fun customerRegister(
        @Header("Authorization") token : String,
        @Part("userDto") userDto : CustomerCreateRequestDto
    ) :  Call<CustomerDto>
}