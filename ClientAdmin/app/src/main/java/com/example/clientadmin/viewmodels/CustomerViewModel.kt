package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.CustomerSummary
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.impl.CustomerApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel : ViewModel() {
    private val customerApiService = CustomerApiServiceImpl()

    private var usernameToSearch = ""
    private var page = 0

    private val _customerSummaries = MutableStateFlow<List<CustomerSummary>>(emptyList())
    val customerSummaries: StateFlow<List<CustomerSummary>> = _customerSummaries

    private val sizePage = 2

    fun incrementAll() {
        page += 1
        loadMoreCustomerSummaries()
    }

    fun setUsernameToSearch(username: String) {
        usernameToSearch = username
        page = 0
        _customerSummaries.value = emptyList()
        loadMoreCustomerSummaries()
    }

    private fun loadMoreCustomerSummaries() {
        CoroutineScope(Dispatchers.IO).launch {
            getCustomerSummaries().collect { newSummaries ->
                _customerSummaries.value += newSummaries
            }
        }
    }

    private fun getCustomerSummaries(): Flow<List<CustomerSummary>> = flow {
        try {
            val response = customerApiService
                .getCustomersSummary(
                    page = page,
                    size = sizePage,
                    username = if (usernameToSearch == "") null else usernameToSearch
                )
                .awaitResponse()

            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching customer summaries: ${response.message()}")

        } catch (e: Exception) {
            println("Exception fetching customer summaries: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

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
