package com.example.clientuser.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.OrderInfoDto
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
class OrderViewModel: ViewModel() {
    private val _validateTransactionResult = MutableStateFlow("")
    val validateTransactionResult = _validateTransactionResult

    private val _createdOrderWebViewLink = mutableStateOf("")
    val createdOrderWebViewLink: State<String> = _createdOrderWebViewLink

//    fun createCartOrder(orderInfoDto: OrderInfoDto): Flow<Map<String, String>> = flow {
//        try{
//            val response = RetrofitHandler.orderApi.createCartOrder(orderInfoDto).awaitResponse()
//            if(response.isSuccessful) response.body()?.let { emit(it) }
//            else println("Error during the order creation: ${response.message()}")
//        }
//        catch (e : Exception){
//            println("Exception during the order creation: ${e.message}")
//        }
//    }.flowOn(Dispatchers.IO)

    fun createCartOrder(orderInfoDto: OrderInfoDto) {
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.orderApi.createCartOrder(orderInfoDto).awaitResponse()
                if(response.isSuccessful) {
                    response.body()?.let { _createdOrderWebViewLink.value = it["url"] ?: "empty link" }
                }
                else println("Error during the order creation: ${response.message()}")
            }
        }
        catch (e : Exception){
            println("Exception during the order creation: ${e.message}")
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