package com.example.clientadmin.model

import com.example.clientadmin.model.enumerator.OrderStatus
import java.time.LocalDate

class Order(
    val id: String,
    var totalPrice: Double,     //TODO conviene renderlo private?
    val address: AddressEmbeddable,
    val date: LocalDate,
    val status: OrderStatus,
    val customer: Customer,
    val items: List<OrderItem>,
){
    fun calculateTotalPrice(){
        this.totalPrice = items.stream().mapToDouble { it.getPrice() }.sum()
    }
}