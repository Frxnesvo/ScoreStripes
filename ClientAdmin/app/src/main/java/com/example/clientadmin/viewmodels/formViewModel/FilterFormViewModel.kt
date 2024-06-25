package com.example.clientadmin.viewmodels.formViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FilterState (
    val name: String = "",
    val league: String = "",
    val category: String = ""
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

    fun build(): Map<String, String> {
        return mapOf(
            "name" to _filterState.value.name,
            "league" to _filterState.value.league,
            "category" to _filterState.value.category
        )
    }
}