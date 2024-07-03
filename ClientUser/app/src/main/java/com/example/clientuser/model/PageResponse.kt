package com.example.clientuser.model

import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.dto.PageResponseDto

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
        fun fromDto(pageResponseDto: PageResponseDto<BasicProductDto>): Page {
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