package com.example.clientadmin.model

class FilterBuilder {
    private var name: String? = null
    private var league: String? = null
    private var category: String? = null
    private var size: String? = null

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

    fun setSize(size: String?): FilterBuilder {
        this.size = size
        return this
    }

    fun build(): Map<String, String?> {
        return mapOf(
            "name" to name,
            "league" to league,
            "category" to category,
            "size" to size
        ).filterValues { it != null }
    }
}
