package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.FilterBuilder
import com.example.clientuser.model.dto.ProductDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(): ViewModel() {
    private var filter: Map<String, String?> = mapOf()
    private var page = 0

    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    val products: StateFlow<List<ProductDto>> = _products

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

    //TODO da finire
}