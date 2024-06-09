package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.FilterBuilder
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
    private var filter: Map<String, String?> = mapOf()
    private var page = 0

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products

    private val _mostSoldProducts = fetchMostSoldProducts()
    val mostSoldProducts = _mostSoldProducts

    private val sizePage = 2

    init {
        //loadMoreProductSummaries()
    }

    fun incrementPage() {
        page += 1
        //loadMoreProductSummaries()
    }

    fun setFilter(filter: Map<String, String?>) {
        this.filter = filter
        page = 0
        _products.value = emptyList()
        //loadMoreProductSummaries()
    }


    //TODO usare BasicProduct?
    private fun fetchMostSoldProducts() : Flow<List<Product>> = flow {
        try{
            val response = RetrofitHandler.productApi.getMostSoldProduct().awaitResponse()
            if(response.isSuccessful) response.body()?.let {
                val newList = it.map { Product.fromDto(it) }
                emit(newList)
            }
            else println("Error during the get of the most selling products: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the get of the most selling products: ${e.message}")
        }
    }

    //TODO da finire
}