package com.example.clientuser.api

import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.dto.PageResponseDto
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.enumerator.ProductCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ProductApiService {
    @GET("/api/v1/products/{id}")
    fun getProductById(@Path("id") id: String): Call<ProductDto>


    @GET("/api/v1/products/more-sold")
    fun getMoreSoldProduct(@Query("category") category: ProductCategory): Call<List<ProductDto>>

    @GET("/api/v1/products")
    fun getProductsSummary(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @QueryMap filters: Map<String, String?>
    ): Call<PageResponseDto<BasicProductDto>>
}