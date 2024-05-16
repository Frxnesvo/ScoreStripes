package com.example.clientadmin.service.interfaces

import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.OrderDto
import retrofit2.Response
import retrofit2.http.GET

interface CustomerApiService {
    @GET("/api/v1/customers/{id}/profile")
    fun getCustomerProfile(): Response<CustomerProfileDto>
    @GET("/api/v1/customers/{id}/addresses")
    fun getCustomerAddresses(): Response<List<AddressDto>>
    @GET("/api/v1/customers/{id}/orders")
    fun getCustomerOrders(): Response<List<OrderDto>>
}