package com.example.clientadmin.service.interfaces

import retrofit2.Call
import retrofit2.http.GET

interface ProductVariantApiService {
    @GET("/api/v1/product-variants/out-of-stock")
    fun countVariantsOutOfStock(): Call<Int>
}