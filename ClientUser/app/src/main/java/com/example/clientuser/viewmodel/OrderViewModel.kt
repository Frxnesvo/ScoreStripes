package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.OrderInfoDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class OrderViewModel: ViewModel() {

    private val _validateTransactionResult = MutableStateFlow("")
    val validateTransactionResult = _validateTransactionResult

    fun createCartOrder(orderInfoDto: OrderInfoDto){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.orderApi.createCartOrder(orderInfoDto).awaitResponse()
                if(response.isSuccessful) TODO()
                else println("Error during the order creation: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception during the order creation: ${e.message}")
            }
        }
    }

    fun validateTransaction(sessionId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.orderApi.validateTransaction(sessionId).awaitResponse()
                if(response.isSuccessful) response.body()?.let { result -> _validateTransactionResult.value = result}
                else println("Error during the transaction verification: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception during the transaction verification: ${e.message}")
            }
        }
    }
}