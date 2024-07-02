package com.example.clientuser.api

import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.model.dto.OrderDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CustomerApiService {
    @GET("/api/v1/customers/{id}/addresses")
    fun getAllAddress(@Path("id") customerId: String): Call<List<AddressDto>>

    @GET("/api/v1/customers/{id}/orders")
    fun getAllOrders(@Path("id") id: String): Call<List<OrderDto>>

    @POST("/api/v1/customers/address")
    fun addAddress(@Body addressCreateRequestDto: AddressCreateRequestDto): Call<AddressDto>

    @DELETE("/api/v1/customers/address/{id}")
    fun deleteAddress(@Path("id") id: String): Call<Map<String, String>>

    @Multipart
    @PATCH("/api/v1/customers/update-customer")
    fun updateCustomer(
        @Part profilePic: MultipartBody.Part?,
        @Part("favoriteTeam") favoriteTeam: RequestBody?
    ): Call<Map<String, String>>
}