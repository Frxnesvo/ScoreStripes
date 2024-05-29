package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.ProductSummary
import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
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
            val response = RetrofitHandler.productApi.getProductsSummary(
                    page,
                    sizePage,
                    filter
                ).awaitResponse()

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
            val response = RetrofitHandler.productApi.getProductById(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                println("Error fetching product details: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching product details: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun addProduct(context: Context, productCreateRequestDto: ProductCreateRequestDto, pic1: Uri, pic2: Uri): Flow<ProductDto> = flow {
        try {
            val response = RetrofitHandler.productApi.createProduct(
                productCreateRequestDto = productCreateRequestDto,
                pic1 = ConverterUri.convert(context = context, uri = pic1, fieldName = "pic1")!!,
                pic2 = ConverterUri.convert(context = context, uri = pic2, fieldName = "pic2")!!,
            ).awaitResponse()

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
