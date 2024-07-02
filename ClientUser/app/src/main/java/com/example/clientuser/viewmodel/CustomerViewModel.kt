package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.Address
import com.example.clientuser.model.Order
import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.utils.RetrofitHandler
import com.example.clientuser.utils.ToastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomerViewModel(private val customerId: String): ViewModel() {
    private val _addresses = MutableStateFlow<List<AddressDto>>(emptyList())
    val addresses = _addresses.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    init{
        fetchAllAddress()
        fetchAllOrders()
    }

    private fun fetchAllAddress(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.getAllAddress(customerId).awaitResponse()
                if(response.isSuccessful) response.body()?.let { _addresses.value += it }
                else println("Error fetching customer addresses ${response.message()}")
            }
        } catch (e : Exception){
            println("Exception fetching customer addresses: ${e.message}")
        }
    }

    private fun fetchAllOrders() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.getAllOrders(customerId).awaitResponse()
                if (response.isSuccessful) response.body()?.let {
                    _orders.value += it.map { order -> Order.fromDto(order) }
                }
                else println("Error fetching customer orders: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching customer orders: ${e.message}")
        }
    }

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
                    response.body()?.let { addressDto ->
                        if(addressDto.defaultAddress)
                            _addresses.value = _addresses.value.map { address ->
                                if(address.defaultAddress) address.copy(defaultAddress = false)
                                else address
                            }
                        _addresses.value += addressDto
                        ToastManager.show("Address added successfully")
                    }
                else {
                    println("Error adding address ${response.message()}")
                    ToastManager.show("Error adding address")
                }
            }
        } catch (e: Exception) {
            println("Exception adding address: ${e.message}")
        }
    }

    //TODO updateCustomer()
}