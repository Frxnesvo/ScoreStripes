package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.model.ProductSummary
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("/api/v1/products/{id}")
    fun getProductById(@Path("id") id: String): Call<ProductDto>

    @Multipart
    @POST("/api/v1/products")
    fun createProduct(@Part("productCreateRequestDto") productCreateRequestDto: ProductCreateRequestDto): Call<ProductDto>

    @GET("/api/v1/products/summary")
    fun getProductsSummary(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<List<ProductSummary>>
}