package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.CustomerApiService
import retrofit2.Response

class CustomerApiServiceImpl : CustomerApiService {
    override fun getCustomerProfile(id: String): Response<CustomerProfileDto> {
        return RetrofitHandler.getCustomerApi().getCustomerProfile(id)
    }

    override fun getCustomerAddresses(id: String): Response<List<AddressDto>> {
        return RetrofitHandler.getCustomerApi().getCustomerAddresses(id)
    }

    override fun getCustomerOrders(id: String): Response<List<OrderDto>> {
        return RetrofitHandler.getCustomerApi().getCustomerOrders(id)
    }

    override fun getCustomersSummary(page: Int, size: Int, username: String?): Response<List<CustomerSummaryDto>> {
        return RetrofitHandler.getCustomerApi().getCustomersSummary(page, size, username)
    }
}
