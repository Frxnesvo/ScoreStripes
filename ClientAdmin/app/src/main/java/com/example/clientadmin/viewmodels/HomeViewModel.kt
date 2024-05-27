package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.service.impl.CustomerApiServiceImpl
import com.example.clientadmin.service.impl.OrdersApiServiceImpl
import com.example.clientadmin.service.impl.ProductVariantApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse

class HomeViewModel: ViewModel() {
    private val customerApiService = CustomerApiServiceImpl()
    private val ordersApiService = OrdersApiServiceImpl()
    private val productVariantApiService = ProductVariantApiServiceImpl()

    fun countNewAccounts(): Flow<Long> = flow {
        try {
            val response = customerApiService.countNewAccounts().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching new accounts")
        } catch (e: Exception) {
            println("Exception fetching new accounts: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun countNewOrders(): Flow<Long> = flow {
        try {
            val response = ordersApiService.countNewOrders().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching new orders")
        } catch (e: Exception) {
            println("Exception fetching new orders: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun countVariantsOutOfStock(): Flow<Int> = flow {
        try {
            val response = productVariantApiService.countVariantsOutOfStock().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching products out of stock")
        } catch (e: Exception) {
            println("Exception fetching products out of stock: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}