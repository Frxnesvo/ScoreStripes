package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.model.ProductSummary
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ProductApiService {
    @GET("/api/v1/products/{id}")
    fun getProductById(@Path("id") id: String): Call<ProductDto>

    @Multipart
    @POST("/api/v1/products")
    fun createProduct(
        @Part("productCreateRequestDto") productCreateRequestDto: ProductCreateRequestDto,
        @Part pic1: MultipartBody.Part,
        @Part pic2: MultipartBody.Part
    ): Call<ProductDto>

    @GET("/api/v1/products/summary")
    fun getProductsSummary(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @QueryMap filters: Map<String, String?>
    ): Call<List<ProductSummary>>
}