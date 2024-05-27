package com.example.clientadmin.service.impl

import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.OrdersApiService
import retrofit2.Call

class OrdersApiServiceImpl: OrdersApiService {
    override fun countNewOrders(): Call<Long> {
        return RetrofitHandler.getOrdersApi().countNewOrders()
    }
}