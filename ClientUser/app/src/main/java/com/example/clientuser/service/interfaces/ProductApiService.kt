package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.dto.ProductSummaryDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ProductApiService {
    @GET("/api/v1/products/")//TODO mettere il resto del path in base al backend
    fun getMostSoldProduct() : Call<List<ProductDto>>

    @GET("/api/v1/products/summary")
    fun getProductsSummary(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @QueryMap filters: Map<String, String?>
    ): Call<List<ProductSummaryDto>>
}