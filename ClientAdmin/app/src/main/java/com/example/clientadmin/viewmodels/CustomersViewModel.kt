package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.CustomerSummary
import com.example.clientadmin.model.Page
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.model.dto.PageResponseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CustomersViewModel : ViewModel() {
    private val _page = MutableStateFlow(Page())
    val page: StateFlow<Page> = _page

    private var _filters: Map<String, String> = mapOf()

    private val _customerSummaries = MutableStateFlow<List<CustomerSummary>>(emptyList())
    val customerSummaries: StateFlow<List<CustomerSummary>> = _customerSummaries

    init { loadMoreCustomerSummaries(_page.value.number) }

    fun incrementPage() {
        if (!_page.value.last) {
            loadMoreCustomerSummaries(_page.value.number + 1)
        }
    }

    fun setFilter(filters: Map<String, String>?) {
        this._filters = filters ?: mapOf()
        _customerSummaries.value = emptyList()
        _page.value = Page()
        loadMoreCustomerSummaries(_page.value.number)
    }

    private fun loadMoreCustomerSummaries(numberPage: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            getCustomerSummaries(numberPage).collect {
                val newSummaries = it.content.map { summary -> CustomerSummary.fromDto(summary) }
                _customerSummaries.value += newSummaries
                _page.value = Page.fromDto(it)
            }
        }
    }

    private fun getCustomerSummaries(numberPage: Int): Flow<PageResponseDto<CustomerSummaryDto>> = flow {
        try {
            val response = RetrofitHandler.customerApi
                .getCustomersSummary(
                    page = numberPage,
                    size = _page.value.size,
                    filters = _filters
                )
                .awaitResponse()

            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching customer summaries: ${response.message()}")

        } catch (e: Exception) {
            println("Exception fetching customer summaries: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}
