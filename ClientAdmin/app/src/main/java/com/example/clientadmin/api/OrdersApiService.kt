package com.example.clientadmin.api

import retrofit2.Call
import retrofit2.http.GET

interface OrdersApiService {
    @GET("/api/v1/orders/new-orders")
    fun countNewOrders(): Call<Long>
}