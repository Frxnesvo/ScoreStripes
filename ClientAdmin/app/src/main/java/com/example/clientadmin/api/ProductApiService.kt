package com.example.clientadmin.api

import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.model.dto.ProductSummaryDto
import com.example.clientadmin.model.enumerator.Size
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
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
        @Part name: MultipartBody.Part,
        @Part description: MultipartBody.Part,
        @Part price: MultipartBody.Part,
        @Part brand: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part category: MultipartBody.Part,
        @Part picPrincipal: MultipartBody.Part,
        @Part pic2: MultipartBody.Part,
        @Part club: MultipartBody.Part,
        @Part variants: MultipartBody.Part,
    ): Call<ProductDto>

    @Multipart
    @PATCH("/api/v1/products/{id}")
    fun updateProduct(
        @Path("id") id: String,
        @Part("description") description: String,
        @Part("price") price: Double,
        @Part picPrincipal: MultipartBody.Part,
        @Part pic2: MultipartBody.Part,
        @Part("variants") variants: Map<Size, Int>,
    ): Call<ProductDto>


    @GET("/api/v1/products/summary")
    fun getProductsSummary(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @QueryMap filters: Map<String, String?>
    ): Call<List<ProductSummaryDto>>
}