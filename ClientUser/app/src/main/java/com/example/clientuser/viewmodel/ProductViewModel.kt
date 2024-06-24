package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.FilterBuilder
import com.example.clientuser.model.Product
import com.example.clientuser.model.ProductSummary
import com.example.clientuser.model.dto.ProductSummaryDto
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
    private var filter: Map<String, String?> = FilterBuilder().build()
    private var page = 0

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products

    private val _mostSoldProducts = fetchMostSoldProducts()
    val mostSoldProducts = _mostSoldProducts

    private val _productSummaries = MutableStateFlow<List<ProductSummary>>(emptyList())
    val productSummary = _productSummaries

    private val sizePage = 2

    init {
        loadMoreProductSummaries()
    }


    fun incrementPage() {
        page += 1
        loadMoreProductSummaries()
    }

    fun setFilter(filter: Map<String, String?>) {
        this.filter = filter
        page = 0
        _products.value = emptyList()
        loadMoreProductSummaries()
    }

    private fun loadMoreProductSummaries() {
        CoroutineScope(Dispatchers.IO).launch {
            getProductSummaries(page).collect {
                val newSummaries = it.map {
                        dto ->  ProductSummary.fromDto(dto)
                }
                _productSummaries.value += newSummaries
            }
        }
    }

    private fun getProductSummaries(page: Int): Flow<List<ProductSummaryDto>> = flow {
        try {
            val response = RetrofitHandler.productApi.getProductsSummary(
                page,
                sizePage,
                filter
            ).awaitResponse()

            if (response.isSuccessful) response.body()?.let { emit(it.content) }
            else println("Error fetching product summaries: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching product summaries: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)


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
}