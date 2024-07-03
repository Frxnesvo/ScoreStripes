package com.example.clientadmin.model

import com.example.clientadmin.model.dto.PageResponseDto

class Page(
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
) {
    constructor() : this(0, 0, 1, 0, 0, true, false, false)
    companion object {
        fun <T> fromDto(pageResponseDto: PageResponseDto<T>): Page {
            return Page(
                totalPages = pageResponseDto.totalPages,
                totalElements = pageResponseDto.totalElements,
                size = pageResponseDto.size,
                number = pageResponseDto.number,
                numberOfElements = pageResponseDto.numberOfElements,
                first = pageResponseDto.first,
                last = pageResponseDto.last,
                empty = pageResponseDto.empty
            )
        }
    }
}