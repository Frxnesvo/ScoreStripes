package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.CustomerSummary
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.CustomerApiService
import retrofit2.Call

class CustomerApiServiceImpl : CustomerApiService {
    override fun getCustomerProfile(id: String): Call<CustomerProfileDto> {
        return RetrofitHandler.getCustomerApi().getCustomerProfile(id)
    }

    override fun getCustomerAddresses(id: String): Call<List<AddressDto>> {
        return RetrofitHandler.getCustomerApi().getCustomerAddresses(id)
    }

    override fun getCustomerOrders(id: String): Call<List<OrderDto>> {
        return RetrofitHandler.getCustomerApi().getCustomerOrders(id)
    }

    override fun countNewAccounts(): Call<Long> {
        return RetrofitHandler.getCustomerApi().countNewAccounts()
    }

    override fun getCustomersSummary(page: Int, size: Int, filters: Map<String, String?>): Call<List<CustomerSummary>> {
        return RetrofitHandler.getCustomerApi().getCustomersSummary(page, size, filters)
    }
}
