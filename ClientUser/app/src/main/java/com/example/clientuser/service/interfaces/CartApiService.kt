package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.CartItemDto
import com.example.clientuser.model.dto.UpdateCartItemDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {

    @POST("/api/v1/carts/add-product")
    fun addProductToCart(@Body addToCartRequestDto: AddToCartRequestDto): Call<CartItemDto>

    @GET("/api/v1/carts/my-cart")
    fun getMyCart(): Call<List<CartItemDto>>

    @PATCH("api/v1/carts/item/{itemId}")
    fun updateItemCartQuantity(@Path("itemId") itemId: String, updateCartItemDto: UpdateCartItemDto): Call<String>

    @DELETE("api/v1/carts/item/{itemId}")
    fun deleteItemFromCart(@Path("itemId") itemId: String): Call<String>
}