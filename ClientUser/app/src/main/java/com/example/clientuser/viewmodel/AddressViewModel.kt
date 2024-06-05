package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class AddressViewModel(private val customerId: String): ViewModel() {
    private val _addresses = MutableStateFlow<List<AddressDto>>(emptyList())
    val addresses : StateFlow<List<AddressDto>> = _addresses

    init{
        fetchAllAddress(customerId)
    }
    private fun fetchAllAddress(customerId: String) {
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.addressApi.getAllAddress(customerId).awaitResponse()
                if(response.isSuccessful)
                    response.body()?.let { _addresses.value = it }
                else println("Error getting address ${response.message()}")
            }
        }
        catch (e : Exception){
            println("Exception getting address: ${e.message}")
        }
    }

    fun addAddress(addressCreateRequestDto: AddressCreateRequestDto){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.addressApi.addAddress(customerId, addressCreateRequestDto).awaitResponse()
                if(response.isSuccessful)
                    response.body()?.let { _addresses.value += it }
                else println("Error adding address ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception adding address: ${e.message}")
        }
    }

}