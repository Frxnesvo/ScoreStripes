package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Page
import com.example.clientadmin.model.ProductSummary
import com.example.clientadmin.model.dto.ProductSummaryDto
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.model.dto.PageResponseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProductsViewModel: ViewModel() {
    private val _page = MutableStateFlow(Page())
    val page: StateFlow<Page> = _page //TODO vedere se esporre solo il lastpage

    private val _productSummaries = MutableStateFlow<List<ProductSummary>>(emptyList())
    val productSummaries: StateFlow<List<ProductSummary>> = _productSummaries

    private var _filters: Map<String, String> = mapOf()

    init { loadMoreProductSummaries(_page.value.number) }

    fun incrementPage() {
        if (!_page.value.last) {
            loadMoreProductSummaries(_page.value.number + 1)
        }
    }

    fun setFilter(filters: Map<String, String>?) {
        _filters = filters ?: mapOf()
        _productSummaries.value = emptyList()
        _page.value = Page()
        loadMoreProductSummaries(_page.value.number)
    }

    private fun loadMoreProductSummaries(numberPage: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            getProductSummaries(numberPage).collect { summaries ->
                val newSummaries = summaries.content.map { dto ->  ProductSummary.fromDto(dto) }
                _productSummaries.value += newSummaries
            }
        }
    }
    private fun getProductSummaries(numberPage: Int): Flow<PageResponseDto<ProductSummaryDto>> = flow {
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
}
