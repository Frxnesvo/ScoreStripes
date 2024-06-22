package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.CustomerSummary
import com.example.clientadmin.model.FilterBuilder
import com.example.clientadmin.model.Order
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.model.dto.PageResponseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel : ViewModel() {
    private var filter: Map<String, String?> = FilterBuilder().build()
    private var page = 0

    private val _customerSummaries = MutableStateFlow<List<CustomerSummary>>(emptyList())
    val customerSummaries: StateFlow<List<CustomerSummary>> = _customerSummaries

    private val sizePage = 2

    init {
        loadMoreCustomerSummaries()
    }

    fun incrementPage() {
        page += 1
        loadMoreCustomerSummaries()
    }

    fun setFilters(filter: Map<String, String?>) {
        this.filter = filter
        page = 0
        _customerSummaries.value = emptyList()
        loadMoreCustomerSummaries()
    }

    private fun loadMoreCustomerSummaries() {
        CoroutineScope(Dispatchers.IO).launch {
            getCustomerSummaries().collect {
                val newSummaries = it.content.map { summary -> CustomerSummary.fromDto(summary) }
                _customerSummaries.value += newSummaries
            }
        }
    }

    private fun getCustomerSummaries(): Flow<PageResponseDto<CustomerSummaryDto>> = flow {
        try {
            val response = RetrofitHandler.customerApi
                .getCustomersSummary(
                    page = page,
                    size = sizePage,
                    filters = filter
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
            val response = RetrofitHandler.customerApi.getCustomerProfile(id).awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching customer details: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching customer details: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getCustomerAddresses(id: String): Flow<List<AddressDto>> = flow {
        try {
            val response = RetrofitHandler.customerApi.getCustomerAddresses(id).awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching customer addresses: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching customer addresses: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getCustomerOrders(id: String): Flow<List<Order>> = flow {
        try {
            val response = RetrofitHandler.customerApi.getCustomerOrders(id).awaitResponse()
            if (response.isSuccessful) response.body()?.let { orders -> emit(orders.map{ Order.fromDto(it) }) }
            else println("Error fetching customer orders: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching customer orders: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}
