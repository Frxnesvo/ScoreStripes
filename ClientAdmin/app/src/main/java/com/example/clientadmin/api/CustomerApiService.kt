package com.example.clientadmin.api

import com.example.clientadmin.model.dto.PageResponseDto
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.model.dto.OrderDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CustomerApiService {
    @GET("/api/v1/customers/{id}/profile")
    fun getCustomerProfile(@Path("id") id: String): Call<CustomerProfileDto>
    @GET("/api/v1/customers/{id}/addresses")
    fun getCustomerAddresses(@Path("id") id: String): Call<List<AddressDto>>
    @GET("/api/v1/customers/{id}/orders")
    fun getCustomerOrders(@Path("id") id: String): Call<List<OrderDto>>
    @GET("/api/v1/customers/new-accounts")
    fun countNewAccounts(): Call<Long>

    @GET("/api/v1/customers/summary")
    fun getCustomersSummary(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @QueryMap filters: Map<String, String>?
    ): Call<PageResponseDto<CustomerSummaryDto>>
}