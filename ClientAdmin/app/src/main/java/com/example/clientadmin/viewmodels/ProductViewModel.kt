package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.ProductSummary
import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.service.impl.ProductApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
    private val productApiService = ProductApiServiceImpl()

    private val page = MutableStateFlow(0)

    private val _productSummaries = MutableStateFlow<List<ProductSummary>>(emptyList())
    val productSummaries: StateFlow<List<ProductSummary>> = _productSummaries

    init {
        CoroutineScope(Dispatchers.IO).launch {
            page.collect { currentPage ->
                fetchProductSummaries(currentPage, 2)
            }
        }
    }

    fun incrementPage() {
        page.value += 1
    }

    private fun fetchProductSummaries(page: Int, size: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = productApiService.getProductsSummary(page, size).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _productSummaries.value += it }
                } else {
                    println("Error fetching product summaries: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Exception fetching product summaries: ${e.message}")
            }
        }
    }

    fun getProduct(id: String): Flow<ProductDto> = flow {
        try {
            val response = productApiService.getProductById(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error fetching product details: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching product details: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun addProduct(productCreateRequestDto: ProductCreateRequestDto): Flow<ProductDto> = flow {
        try {
            val response = productApiService.createProduct(productCreateRequestDto).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error creating product: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception creating product: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}