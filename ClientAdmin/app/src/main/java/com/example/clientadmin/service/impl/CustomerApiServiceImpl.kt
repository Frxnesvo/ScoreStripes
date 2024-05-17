package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerApiServiceImpl {
    suspend fun getCustomerDetails(id: String): CustomerProfileDto?{
        return withContext(Dispatchers.IO){
             try {
                 val response = RetrofitHandler.getCustomerApi().getCustomerProfile(id)
                 if (response.isSuccessful) response.body()
                 else null
             }
             catch (e: Exception) {
                 e.printStackTrace()
                 null
             }
        }
    }

    suspend fun getCustomerAddresses(id: String): List<AddressDto>{
        return withContext(Dispatchers.IO){
            try {
                val response = RetrofitHandler.getCustomerApi().getCustomerAddresses(id)
                if (response.isSuccessful) response.body() ?: emptyList()
                else emptyList()
            }
            catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun getCustomerOrders(id: String): List<OrderDto>{
        return withContext(Dispatchers.IO){
            try {
                val response = RetrofitHandler.getCustomerApi().getCustomerOrders(id)
                if (response.isSuccessful) response.body() ?: emptyList()
                else emptyList()
            }
            catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}