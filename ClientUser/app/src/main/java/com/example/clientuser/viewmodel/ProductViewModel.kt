package com.example.clientuser.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.BasicProduct
import com.example.clientuser.model.FilterBuilder
import com.example.clientuser.model.Product
import com.example.clientuser.model.ProductSummary
import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.dto.ProductSummaryDto
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
    private var filter: Map<String, String?> = FilterBuilder().build()
    private var page = 0

//    private val _products = MutableStateFlow<List<Product>>(emptyList())
//    val products = _products


    private val _productSummaries = MutableStateFlow<List<BasicProduct>>(emptyList())
    val productSummary = _productSummaries

    private val _moreSoldJersey = MutableStateFlow<List<Product>>(emptyList())
    private val _moreSoldShorts = MutableStateFlow<List<Product>>(emptyList())

    val moreSoldJersey = _moreSoldJersey.asStateFlow()
    val moreSoldShorts = _moreSoldShorts.asStateFlow()

    private val sizePage = 2

    init {
        loadMoreProductSummaries()
        fetchMoreSoldProduct(ProductCategory.SHORTS)
        fetchMoreSoldProduct(ProductCategory.JERSEY)
    }


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
            getProductSummaries(page).collect {
                val newSummaries = it.map {
                        dto ->  BasicProduct.fromDto(dto)
                }
                _productSummaries.value += newSummaries
            }
        }
    }

    private fun getProductSummaries(page: Int): Flow<List<BasicProductDto>> = flow {
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

    suspend fun getProductById(id: String): Product? {
        return try {
            withContext(Dispatchers.IO) {
                val response = RetrofitHandler.productApi.getProductById(id).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let {
                        Product.fromDto(it)
                    }
                } else {
                    println("Error product get: ${response.message()}")
                    null
                }
            }
        } catch (e: Exception) {
            println("Exception product get: ${e.message}")
            null
        }
    }
}