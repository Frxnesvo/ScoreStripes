package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.CartItemDto
import com.example.clientuser.model.dto.UpdateCartItemDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CartApiService {

    @POST("/api/v1/carts/add-product")
    fun addProductToCart(@Body addToCartRequestDto: AddToCartRequestDto): Call<CartItemDto>

    @GET("/api/v1/carts/my-cart")
    fun getMyCart(): Call<List<CartItemDto>>

    @POST("api/v1/carts/")//TODO aggiungere il resto del path una volta fatto il controller nel backend
    fun updateItemCartQuantity(updateCartItemDto: UpdateCartItemDto): Call<String>  //todo passare il dto
}