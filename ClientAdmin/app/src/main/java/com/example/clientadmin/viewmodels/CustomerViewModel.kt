package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.impl.CustomerApiServiceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel : ViewModel() {
    private val customerApiService = CustomerApiServiceImpl()

    private val _customerSummaries = MutableStateFlow<List<CustomerSummaryDto>>(emptyList())
    val customerSummaries: StateFlow<List<CustomerSummaryDto>> = _customerSummaries

    fun fetchCustomerSummaries(page: Int, size: Int, username: String? = null) {
        viewModelScope.launch {
            try {
                val response = customerApiService.getCustomersSummary(page, size, username).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _customerSummaries.value = it }
                } else {
                    println("Error fetching customer summaries: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Exception fetching customer summaries: ${e.message}")
            }
        }
    }

    fun getCustomerDetails(id: String): Flow<CustomerProfileDto> = flow {
        try {
            val response = customerApiService.getCustomerProfile(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error fetching customer details: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer details: ${e.message}")
        }
    }

    fun getCustomerAddresses(id: String): Flow<List<AddressDto>> = flow {
        try {
            val response = customerApiService.getCustomerAddresses(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error fetching customer addresses: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer addresses: ${e.message}")
        }
    }

    fun getCustomerOrders(id: String): Flow<List<OrderDto>> = flow {
        try {
            val response = customerApiService.getCustomerOrders(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error fetching customer orders: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer orders: ${e.message}")
        }
    }
}
