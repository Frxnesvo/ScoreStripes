package com.example.clientuser.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.Customer
import com.example.clientuser.model.dto.CustomerCreateRequestDto
import com.example.clientuser.model.dto.CustomerDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LoginViewModel: ViewModel() {
    //todo mettere il customerDto
    private val _user = MutableStateFlow<CustomerDto?>(null)
    val user: StateFlow<CustomerDto?> = _user

    fun getCustomerFromToken(token: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.loginApi.checkCustomerLogin("Bearer $token").awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _user.value = TODO() }
                } else {
                    println("Error checking customer login: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Exception checking customer login: ${e.message}")
            }
        }
    }

    fun register(token: String, customerCreateRequestDto: CustomerCreateRequestDto, context: Context){
        TODO()
    }

    fun updateCustomer(customerDto: CustomerDto){
        TODO("manca controller per l'update")
    }
}