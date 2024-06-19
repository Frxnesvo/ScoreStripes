package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.Address
import com.example.clientuser.model.Order
import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel(private val customerId: String): ViewModel() {
    private val _addresses = MutableStateFlow<List<AddressDto>>(emptyList())
    val addresses = _addresses

    init{
        fetchAllAddress()
    }

    private fun fetchAllAddress(): Flow<List<AddressDto>> = flow {
        try{
            val response = RetrofitHandler.customerApi.getAllAddress(customerId).awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching customer addresses ${response.message()}")
        } catch (e : Exception){
            println("Exception fetching customer addresses: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getCustomerOrders(): Flow<List<Order>> = flow {
        try {
            val response = RetrofitHandler.customerApi.getAllOrders(customerId).awaitResponse()
            if (response.isSuccessful) response.body()?.let { orders -> emit(orders.map{ Order.fromDto(it) }) }
            else println("Error fetching customer orders: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching customer orders: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun addAddress(addressCreateRequestDto: AddressCreateRequestDto){
        try {
            Address(
                addressCreateRequestDto.street,
                addressCreateRequestDto.city,
                addressCreateRequestDto.civicNumber,
                addressCreateRequestDto.zipCode,
                addressCreateRequestDto.state,
                addressCreateRequestDto.defaultAddress
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.addAddress(addressCreateRequestDto).awaitResponse()
                if(response.isSuccessful)
                    response.body()?.let { _addresses.value += it }
                else println("Error adding address ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception adding address: ${e.message}")
        }
    }

    fun setDefaultAddress(addressId: String){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.setDefaultAddress(addressId).awaitResponse()
                if(response.isSuccessful) fetchAllAddress()
                else println("Error setting default address ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception setting default address: ${e.message}")
        }
    }

    //TODO updateCustomer()
}