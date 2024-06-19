package com.example.clientuser.api

import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.model.dto.OrderDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CustomerApiService {
    @GET("/api/v1/customers/{id}/addresses")
    fun getAllAddress(@Path("id") customerId: String): Call<List<AddressDto>>
    @GET("/api/v1/customers/{id}/orders")
    fun getAllOrders(@Path("id") id: String): Call<List<OrderDto>>

    @POST("/api/v1/customers/address")
    fun addAddress(
        @Body addressCreateRequestDto: AddressCreateRequestDto
    ): Call<AddressDto>
    @POST("/api/v1/customers/address/set-default")
    fun setDefaultAddress(
        @Body addressId: String //todo da matchare con il backend
    ): Call<AddressDto>
}