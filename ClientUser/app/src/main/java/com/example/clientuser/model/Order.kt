package com.example.clientuser.model

import com.example.clientuser.model.dto.OrderDto
import com.example.clientuser.model.dto.OrderItemDto
import com.example.clientuser.model.embdeddables.OrderInformations
import com.example.clientuser.model.enumerator.OrderStatus
import java.time.LocalDate

class Order(
    val id: String,
    val totalPrice: Double,
    val date: LocalDate,
    val status: OrderStatus,
    val resilientInfos: OrderInformations,
    val items: List<OrderItem>
) {
    companion object {
        suspend fun fromDto(orderDto: OrderDto): Order {
            return Order(
                id = orderDto.id,
                totalPrice = orderDto.totalPrice,
                date = orderDto.date,
                status = orderDto.status,
                resilientInfos = orderDto.resilientInfos,
                items = orderDto.items.map { OrderItem.fromDto(it) }
            )
        }
    }
    class OrderItem(
        val id: String,
        val quantity: Int,
        val price: Double,
        val personalization: Personalization,
        val product: ProductWithVariant
    ) {
        companion object {
            suspend fun fromDto(orderItemDto: OrderItemDto): OrderItem {
                return OrderItem(
                    id = orderItemDto.id,
                    quantity = orderItemDto.quantity,
                    price = orderItemDto.price,
                    personalization = orderItemDto.personalization,
                    product = ProductWithVariant.fromDto(orderItemDto.product)
                )
            }
        }
    }
}