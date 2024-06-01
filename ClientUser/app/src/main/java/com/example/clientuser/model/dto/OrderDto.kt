package com.example.clientuser.model.dto

import com.example.clientuser.model.embdeddables.OrderInformations
import com.example.clientuser.model.enumerator.OrderStatus
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class OrderDto (
    val id: String,
    val totalPrice: Double,
    val date: LocalDate,
    val status: OrderStatus,
    val resilientInfos: OrderInformations,
    val items: List<OrderItemDto>
)