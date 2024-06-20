package com.example.clientadmin.model

import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.model.dto.OrderItemDto
import com.example.clientadmin.model.embdeddables.Personalization
import com.example.clientadmin.model.dto.ProductWithVariantDto
import com.example.clientadmin.model.embdeddables.OrderInformations
import com.example.clientadmin.model.enumerator.OrderStatus
import com.example.clientadmin.model.enumerator.Size
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
        fun fromDto(orderDto: OrderDto): Order {
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
            fun fromDto(orderItemDto: OrderItemDto): OrderItem {
                return OrderItem(
                    id = orderItemDto.id,
                    quantity = orderItemDto.quantity,
                    price = orderItemDto.price,
                    personalization = orderItemDto.personalization,
                    product = ProductWithVariant.fromDto(orderItemDto.product)
                )
            }
        }
        class ProductWithVariant(
            val id: String,
            val size: Size,
            val product: Product
        ) {
            companion object {
                fun fromDto(productWithVariantDto: ProductWithVariantDto): ProductWithVariant {
                    return ProductWithVariant(
                        id = productWithVariantDto.id,
                        size = productWithVariantDto.size,
                        product = Product.fromDto(productWithVariantDto.product) //todo fare il basicdto
                    )
                }
            }
        }

    }
}