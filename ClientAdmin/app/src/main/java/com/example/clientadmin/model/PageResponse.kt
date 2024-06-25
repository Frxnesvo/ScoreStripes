package com.example.clientadmin.model

import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.model.dto.PageResponseDto
import com.example.clientadmin.model.dto.ProductSummaryDto

class Page(
    val totalPages: Int = 0,
    val totalElements: Long = 0,
    val size: Int = 2,
    val number: Int = 0,
    val numberOfElements: Int = 0,
    val first: Boolean = true,
    val last: Boolean = false,
    val empty: Boolean = false
) {
    companion object {
        fun fromDto(pageResponseDto: PageResponseDto<Any>): Page {
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