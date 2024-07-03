package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.BasicProduct
import com.example.clientuser.model.Page
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.dto.PageResponseDto
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.utils.RetrofitHandler
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

class ProductsViewModel: ViewModel() {
    private val _products = MutableStateFlow<List<BasicProduct>>(emptyList())
    val products = _products

    private val _moreSoldJersey = MutableStateFlow<List<Product>>(emptyList())
    private val _moreSoldShorts = MutableStateFlow<List<Product>>(emptyList())

    val moreSoldJersey = _moreSoldJersey.asStateFlow()
    val moreSoldShorts = _moreSoldShorts.asStateFlow()

    private val _page = MutableStateFlow(Page())
    val page: StateFlow<Page> = _page


    private var _filters: Map<String, String> = mapOf()

    init {
        loadMoreProductSummaries(_page.value.number)
        fetchMoreSoldProduct(ProductCategory.JERSEY)
        fetchMoreSoldProduct(ProductCategory.SHORTS)
    }

    fun incrementPage() {
        if (!_page.value.last) {
            loadMoreProductSummaries(_page.value.number + 1)
        }
    }

    fun setFilter(filters: Map<String, String>?) {
        _filters = filters ?: mapOf()
        _products.value = emptyList()
        _page.value = Page()
        loadMoreProductSummaries(_page.value.number)
    }

    private fun loadMoreProductSummaries(numberPage: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            getProductSummaries(numberPage).collect { products ->
                val newProducts = products.content.map { dto ->  BasicProduct.fromDto(dto) }
                _products.value += newProducts
            }
        }
    }
    private fun getProductSummaries(numberPage: Int): Flow<PageResponseDto<BasicProductDto>> = flow {
        try {
            val response = RetrofitHandler.productApi.getProductsSummary(
                page = numberPage,
                size = _page.value.size,
                filters = _filters
            ).awaitResponse()

            if (response.isSuccessful) response.body()?.let { emit(it) }
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
}