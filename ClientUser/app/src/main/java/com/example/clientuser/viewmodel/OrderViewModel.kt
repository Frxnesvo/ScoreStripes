package com.example.clientuser.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.OrderInfoDto
import com.example.clientuser.model.enumerator.OrderStatus
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch
import retrofit2.awaitResponse
class OrderViewModel: ViewModel() {
    private val _validateTransactionResult = MutableStateFlow("")
    val validateTransactionResult = _validateTransactionResult

    private val _createdOrderWebViewLink = mutableStateOf("")
    val createdOrderWebViewLink: State<String> = _createdOrderWebViewLink

    fun createCartOrder(orderInfoDto: OrderInfoDto): Boolean {
        return try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.orderApi.createCartOrder(orderInfoDto).awaitResponse()
                if(response.isSuccessful) {
                    response.body()?.let { _createdOrderWebViewLink.value = it["url"] ?: "empty link" }
                }
                else {
                    _validateTransactionResult.value = "error"
                    println("Error during the order creation: ${response.message()}")
                }
            }
            true
        }
        catch (e : Exception){
            println("Exception during the order creation: ${e.message}")
            false
        }
    }

    fun validateTransaction(sessionId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.orderApi.validateTransaction(sessionId).awaitResponse()
                if(response.isSuccessful) response.body()?.let {
                    _validateTransactionResult.value = if(it["orderStatus"] == OrderStatus.COMPLETED.name) "payment_success" else "payment_failure"
                }
                else {
                    _validateTransactionResult.value = "error"
                    println("Error during the transaction verification: ${response.message()}")
                }
            }
            catch (e : Exception){
                println("Exception during the transaction verification: ${e.message}")
            }
        }
    }
}