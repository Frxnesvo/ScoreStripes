package com.example.clientadmin.viewmodels.formViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FilterState (
    val name: String = "",
    val league: String = "",
    val category: String = ""
)
class FilterFormViewModel (filter: Map<String, String> = mapOf()) {
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    init {
        _filterState.value = _filterState.value.copy(
            name = filter["name"] ?: "",
            league = filter["league"] ?: "",
            category = filter["category"] ?: ""
        )
    }

    fun updateName(name: String) {
        _filterState.value = _filterState.value.copy(name = name)
    }

    fun updateLeague(league: String) {
        _filterState.value = _filterState.value.copy(league = league)
    }

    fun updateCategory(category: String) {
        _filterState.value = _filterState.value.copy(category = category)
    }

    fun build(): Map<String, String>? {
        var mapToBuild: Map<String, String>? = null
        if (_filterState.value.name.isNotEmpty() || _filterState.value.league.isNotEmpty() || _filterState.value.category.isNotEmpty()) {
            mapToBuild = mapOf(
                "name" to _filterState.value.name,
                "league" to _filterState.value.league,
                "category" to _filterState.value.category
            )
        }
        return mapToBuild
    }
}