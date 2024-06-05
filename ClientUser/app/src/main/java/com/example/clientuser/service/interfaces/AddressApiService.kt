package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AddressApiService {
    @GET("/api/v1/addresses")
    fun getAllAddress(@Query("customerId") customerId: String): Call<List<AddressDto>>
    @POST("/api/v1/add-address")
    fun addAddress(
        @Body customerId: String,
        @Body addressCreateRequestDto: AddressCreateRequestDto
    ): Call<AddressDto>
}