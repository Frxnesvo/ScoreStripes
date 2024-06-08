package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.FilterBuilder
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

    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    val products: StateFlow<List<ProductDto>> = _products

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

    fun fetchMostSoldProducts() : Flow<List<ProductDto>> = flow {
        try{
            val response = RetrofitHandler.productApi.getMostSoldProduct().awaitResponse()
            if(response.isSuccessful) response.body()?.let {emit(it)}
            else println("Error during the get of the most selling products: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the get of the most selling products: ${e.message}")
        }
    }

    //TODO da finire
}