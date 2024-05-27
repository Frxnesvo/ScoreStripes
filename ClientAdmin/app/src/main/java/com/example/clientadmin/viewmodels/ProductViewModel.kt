package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.ProductSummary
import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.service.impl.ProductApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
    private val productApiService = ProductApiServiceImpl()

    private var filter: Map<String, String?> = mapOf()
    private var page = 0

    private val _productSummaries = MutableStateFlow<List<ProductSummary>>(emptyList())
    val productSummaries: StateFlow<List<ProductSummary>> = _productSummaries

    private val sizePage = 2

    fun incrementPage() {
        page += 1
        loadMoreProductSummaries()
    }

    fun setFilter(filter: Map<String, String?>) {
        this.filter = filter
        page = 0
        _productSummaries.value = emptyList()
        loadMoreProductSummaries()
    }

    private fun loadMoreProductSummaries() {
        CoroutineScope(Dispatchers.IO).launch {
            getProductSummaries(page).collect { newSummaries ->
                _productSummaries.value += newSummaries
            }
        }
    }

    private fun getProductSummaries(page: Int): Flow<List<ProductSummary>> = flow {
        try {
            val response = productApiService
                .getProductsSummary(page, sizePage, filter)
                .awaitResponse()

            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error fetching product summaries: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching product summaries: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

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
