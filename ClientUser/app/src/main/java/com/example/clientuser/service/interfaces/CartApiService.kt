package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddToCartRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CartApiService {

    @POST
    fun addProductToCart(@Body addToCartRequestDto: AddToCartRequestDto): Call<String>

}