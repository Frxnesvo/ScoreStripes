package com.example.clientuser.api

import com.example.clientuser.model.dto.OrderInfoDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApiService {
    @POST("/api/v1/orders/create-cart-order")
    fun createCartOrder(@Body orderInfoDto: OrderInfoDto) : Call<Map<String, String>>

    @POST("/api/v1/orders/validate-transaction")
    fun validateTransaction(@Query("sessionId") sessionId: String) : Call<String>
}