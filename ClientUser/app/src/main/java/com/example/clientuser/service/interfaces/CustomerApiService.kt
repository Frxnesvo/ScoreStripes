package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.model.dto.OrderDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomerApiService {
    @GET("/api/v1/addresses")
    fun getAllAddress(@Query("customerId") customerId: String): Call<List<AddressDto>>
    @GET("/api/v1/customers/{id}/orders")
    fun getCustomerOrders(@Path("id") id: String): Call<List<OrderDto>>

    @POST("/api/v1/add-address")
    fun addAddress(
        @Body customerId: String,
        @Body addressCreateRequestDto: AddressCreateRequestDto
    ): Call<AddressDto>
}