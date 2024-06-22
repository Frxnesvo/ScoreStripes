package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.api.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse

class HomeViewModel: ViewModel() {
    private val _newOrders: Flow<Long> = countNewOrders()
    private val _newAccounts: Flow<Long> = countNewAccounts()
    private val _variantsOutOfStock: Flow<Int> = countVariantsOutOfStock()

    val newOrders: Flow<Long> = flowOf(0)
    val newAccounts: Flow<Long> = flowOf(0)
    val variantsOutOfStock: Flow<Int> = flowOf(0)

    fun countNewAccounts(): Flow<Long> = flow {
        try {
            val response = RetrofitHandler.customerApi.countNewAccounts().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching new accounts")
        } catch (e: Exception) {
            println("Exception fetching new accounts: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun countNewOrders(): Flow<Long> = flow {
        try {
            val response = RetrofitHandler.ordersApi.countNewOrders().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching new orders")
        } catch (e: Exception) {
            println("Exception fetching new orders: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun countVariantsOutOfStock(): Flow<Int> = flow {
        try {
            val response = RetrofitHandler.productVariantApi.countVariantsOutOfStock().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching products out of stock")
        } catch (e: Exception) {
            println("Exception fetching products out of stock: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}