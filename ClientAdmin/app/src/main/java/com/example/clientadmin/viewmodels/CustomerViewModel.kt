package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.CustomerSummary
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.impl.CustomerApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel : ViewModel() {
    private val customerApiService = CustomerApiServiceImpl()

    private val page = MutableStateFlow(0)

    private val _customerSummaries = MutableStateFlow<List<CustomerSummary>>(emptyList())
    val customerSummaries: StateFlow<List<CustomerSummary>> = _customerSummaries

    init {
        CoroutineScope(Dispatchers.IO).launch {
            page.collect { currentPage ->
                fetchCustomerSummaries(currentPage, 2)
            }
        }
    }

    fun incrementPage() {
        page.value += 1
    }

    private fun fetchCustomerSummaries(page: Int, size: Int, username: String? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = customerApiService.getCustomersSummary(page, size, username).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _customerSummaries.value += it }
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
    }.flowOn(Dispatchers.IO)

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
    }.flowOn(Dispatchers.IO)

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
    }.flowOn(Dispatchers.IO)
}
