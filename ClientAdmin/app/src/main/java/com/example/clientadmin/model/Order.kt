package com.example.clientadmin.model

import com.example.clientadmin.model.Enum.Size
import java.time.LocalDate

data class OrderProduct(
    val product: Product,
    val size: Size,
    val quantity: Int
)

data class Order(
    val id: Int,
    val userID: Int,
    val products: List<OrderProduct>,
    val date: LocalDate,
    val address: Address,
    val amount: Double
)

