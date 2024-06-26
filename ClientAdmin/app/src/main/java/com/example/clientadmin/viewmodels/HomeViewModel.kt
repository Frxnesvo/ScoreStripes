package com.example.clientadmin.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class HomeViewModel: ViewModel() {
    private val _newOrders = MutableStateFlow<Long>(0)
    private val _newAccounts= MutableStateFlow<Long>(0)
    private val _variantsOutOfStock= MutableStateFlow(0)

    val newOrders: StateFlow<Long> = _newOrders.asStateFlow()
    val newAccounts: StateFlow<Long> = _newAccounts.asStateFlow()
    val variantsOutOfStock: StateFlow<Int> = _variantsOutOfStock.asStateFlow()

    val moreSoldJersey = fetchMoreSoldProduct(ProductCategory.JERSEY)
    val moreSoldShorts = fetchMoreSoldProduct(ProductCategory.SHORTS)

    init {
        countNewAccounts()
        countNewOrders()
        countVariantsOutOfStock()
    }

    private fun countNewAccounts() {
        Log.e("HomeViewModel", "countNewAccounts")
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.customerApi.countNewAccounts().awaitResponse()
                if (response.isSuccessful) response.body()?.let { _newAccounts.value = it}
                else println("Error fetching new accounts")
            }
        } catch (e: Exception) {
            println("Exception fetching new accounts: ${e.message}")
        }
    }

    private fun countNewOrders() {
        Log.e("HomeViewModel", "countNewOrders")
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.ordersApi.countNewOrders().awaitResponse()
                if (response.isSuccessful) response.body()?.let { _newOrders.value = it }
                else println("Error fetching new orders")
            }
        } catch (e: Exception) {
            println("Exception fetching new orders: ${e.message}")
        }
    }

    private fun countVariantsOutOfStock() {
        Log.e("HomeViewModel", "countVariantsOutOfStock")
        try {
            CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        RetrofitHandler.productVariantApi.countVariantsOutOfStock().awaitResponse()
                    if (response.isSuccessful) response.body()?.let { _variantsOutOfStock.value = it }
                    else println("Error fetching products out of stock")
            }
        } catch (e: Exception) {
            println("Exception fetching products out of stock: ${e.message}")
        }
    }

    private fun fetchMoreSoldProduct(category: ProductCategory): Flow<List<Product>> = flow {
        try {
            val response = RetrofitHandler.productApi.getMoreSoldProduct(category).awaitResponse()
            if(response.isSuccessful){
                response.body()?.let { products ->
                    emit(products.map { product -> Product.fromDto(product) })
                }
            }
            else println("Error fetching more sold ${category.name}: ${response.message()}")

        }catch(e: Exception){
            println("Exception fetching more sold ${category.name}: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}