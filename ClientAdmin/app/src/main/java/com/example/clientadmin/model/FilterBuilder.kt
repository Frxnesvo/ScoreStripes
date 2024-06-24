package com.example.clientadmin.model

class FilterBuilder {
    private var name: String? = null
    private var league: String? = null
    private var category: String? = null

    fun setName(name: String?): FilterBuilder {
        this.name = name
        return this
    }

    fun setLeague(league: String?): FilterBuilder {
        this.league = league
        return this
    }

    fun setCategory(category: String?): FilterBuilder {
        this.category = category
        return this
    }

    fun build(): Map<String, String?> {
        return mapOf(
            "name" to name,
            "league" to league,
            "category" to category
        ).filterValues { it != null }
    }
}
