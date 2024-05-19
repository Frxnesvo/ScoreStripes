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

class CustomerViewModel : ViewModel() {
    private val customerApiService = CustomerApiServiceImpl()

    private val _customerSummaries = MutableStateFlow<List<CustomerSummaryDto>>(emptyList())
    val customerSummaries: StateFlow<List<CustomerSummaryDto>> = _customerSummaries

    fun fetchCustomerSummaries(page: Int, size: Int, username: String? = null) {
        viewModelScope.launch {
            val response = customerApiService.getCustomersSummary(page, size, username)
            if (response.isSuccessful) {
                response.body()?.let { _customerSummaries.value = it }
            } else {
                println("Error fetching customer summaries")
            }
        }
    }

    fun getCustomerDetails(id: String): Flow<CustomerProfileDto> = flow {
        val response = customerApiService.getCustomerProfile(id)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            println("Error fetching customer details")
        }
    }

    fun getCustomerAddresses(id: String): Flow<List<AddressDto>> = flow {
        val response = customerApiService.getCustomerAddresses(id)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            println("Error fetching customer addresses")
        }
    }

    fun getCustomerOrders(id: String): Flow<List<OrderDto>> = flow {
        val response = customerApiService.getCustomerOrders(id)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            println("Error fetching customer orders")
        }
    }
}
