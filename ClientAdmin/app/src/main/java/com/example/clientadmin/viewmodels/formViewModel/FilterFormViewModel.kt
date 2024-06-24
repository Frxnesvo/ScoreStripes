package com.example.clientadmin.viewmodels.formViewModel

import com.example.clientadmin.model.FilterBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FilterState (
    val name: String? = null,
    val league: String? = null,
    val category: String? = null,
    val size: String? = null
)
class FilterFormViewModel {
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    fun updateName(name: String) {
        _filterState.value = _filterState.value.copy(name = name)
    }

    fun updateLeague(league: String) {
        _filterState.value = _filterState.value.copy(league = league)
    }

    fun updateCategory(category: String) {
        _filterState.value = _filterState.value.copy(category = category)
    }

    fun updateSize(size: String) {
        _filterState.value = _filterState.value.copy(size = size)
    }

    fun asFilterBuilder(): FilterBuilder {
        return FilterBuilder()
            .setName(_filterState.value.name)
            .setLeague(_filterState.value.league)
            .setCategory(_filterState.value.category)
            .setSize(_filterState.value.size)
    }
}