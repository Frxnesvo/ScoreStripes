package com.example.clientadmin.model.dto

import com.example.clientadmin.model.embdeddables.OrderInformations
import com.example.clientadmin.model.enumerator.OrderStatus
import java.time.LocalDate

data class OrderDto (
    val id: String,
    val totalPrice: Double,
    val date: LocalDate,
    val status: OrderStatus,
    val resilientInfos: OrderInformations,
    val items: List<OrderItemDto>
)