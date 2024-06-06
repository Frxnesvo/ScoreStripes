package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.ProductDto
import retrofit2.Call
import retrofit2.http.GET

interface ProductApiService {
    @GET("/api/v1/products/")//TODO mettere il resto del path in base al backend
    fun getMostSoldProduct() : Call<List<ProductDto>>
}