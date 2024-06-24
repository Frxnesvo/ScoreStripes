package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductSummaryDto(
    val id: String,
    val name: String,
    val clubName: String,
    val leagueName: String,
    val picUrl: String,
    val variantStock: Map<Size, Int>
)
