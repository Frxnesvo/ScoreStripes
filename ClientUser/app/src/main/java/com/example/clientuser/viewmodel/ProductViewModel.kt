package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.Product
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProductViewModel: ViewModel(){
    private val _product = MutableStateFlow(Product())
    val product = _product.asStateFlow()

    fun getProduct(id: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.getProductById(id).awaitResponse()
                if (response.isSuccessful) response.body()?.let {
                    _product.value = Product.fromDto(it)
                }
                else println("Error fetching product details: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching product details: ${e.message}")
        }
    }
}