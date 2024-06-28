package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Order
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel: ViewModel() {
    private val _customerProfile = MutableStateFlow<CustomerProfileDto?>(null)
    val customerProfile: StateFlow<CustomerProfileDto?> = _customerProfile

    private val _customerAddresses = MutableStateFlow<List<AddressDto>>(emptyList())
    val customerAddresses: StateFlow<List<AddressDto>> = _customerAddresses

    private val _customerOrders = MutableStateFlow<List<Order>>(emptyList())
    val customerOrders: StateFlow<List<Order>> = _customerOrders

    fun getCustomerDetails(id: String){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.getCustomerProfile(id).awaitResponse()
                if (response.isSuccessful) response.body()?.let { _customerProfile.value = it }
                else println("Error fetching customer details: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer details: ${e.message}")
        }
    }

    fun getCustomerAddresses(id: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.getCustomerAddresses(id).awaitResponse()
                if (response.isSuccessful) response.body()?.let { _customerAddresses.value = it }
                else println("Error fetching customer addresses: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer addresses: ${e.message}")
        }
    }

    fun getCustomerOrders(id: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.getCustomerOrders(id).awaitResponse()
                if (response.isSuccessful) response.body()?.let {
                    orders -> _customerOrders.value = orders.map { Order.fromDto(it) }
                }
                else println("Error fetching customer orders: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer orders: ${e.message}")
        }
    }
}