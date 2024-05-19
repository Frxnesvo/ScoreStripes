package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.model.dto.OrderDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomerApiService {
    @GET("/api/v1/customers/{id}/profile")
    fun getCustomerProfile(@Path("id") id: String): Response<CustomerProfileDto>

    @GET("/api/v1/customers/{id}/addresses")
    fun getCustomerAddresses(@Path("id") id: String): Response<List<AddressDto>>

    @GET("/api/v1/customers/{id}/orders")
    fun getCustomerOrders(@Path("id") id: String): Response<List<OrderDto>>

    @GET("/api/v1/customers/summary")
    fun getCustomersSummary(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("username") username: String? = null
    ): Response<List<CustomerSummaryDto>>
}