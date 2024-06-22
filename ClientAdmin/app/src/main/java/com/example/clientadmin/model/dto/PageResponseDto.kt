package com.example.clientadmin.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageResponseDto<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

