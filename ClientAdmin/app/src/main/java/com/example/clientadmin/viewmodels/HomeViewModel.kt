package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class HomeViewModel: ViewModel() {
    private val _newOrders = MutableStateFlow<Long>(0)
    private val _newAccounts= MutableStateFlow<Long>(0)
    private val _variantsOutOfStock= MutableStateFlow(0)
    private val _moreSoldJersey = MutableStateFlow<List<Product>>(emptyList())
    private val _moreSoldShorts = MutableStateFlow<List<Product>>(emptyList())

    val newOrders: StateFlow<Long> = _newOrders.asStateFlow()
    val newAccounts: StateFlow<Long> = _newAccounts.asStateFlow()
    val variantsOutOfStock: StateFlow<Int> = _variantsOutOfStock.asStateFlow()
    val moreSoldJersey: StateFlow<List<Product>> = _moreSoldJersey.asStateFlow()
    val moreSoldShorts: StateFlow<List<Product>> = _moreSoldShorts.asStateFlow()

    init {
        countNewAccounts()
        countNewOrders()
        countVariantsOutOfStock()
        fetchMoreSoldProduct(ProductCategory.JERSEY)
        fetchMoreSoldProduct(ProductCategory.SHORTS)
    }

    private fun countNewAccounts() {
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

    private fun fetchMoreSoldProduct(category: ProductCategory) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.getMoreSoldProduct(category).awaitResponse()
                if(response.isSuccessful){
                    response.body()?.let {
                        val products = it.map { product -> Product.fromDto(product) }
                        when(category){
                            ProductCategory.JERSEY -> _moreSoldJersey.value = products
                            ProductCategory.SHORTS -> _moreSoldShorts.value = products
                        }
                    }
                }
                else println("Error fetching more sold ${category.name}: ${response.message()}")
            }
        }catch(e: Exception){
            println("Exception fetching more sold ${category.name}: ${e.message}")
        }
    }
}