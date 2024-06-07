package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.CartItemDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CartApiService {

    @POST("/api/v1/carts/add-product")
    fun addProductToCart(@Body addToCartRequestDto: AddToCartRequestDto): Call<String>

    @GET("/api/v1/carts/my-cart")
    fun getMyCart(): Call<List<CartItemDto>>
}