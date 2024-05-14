package com.example.clientadmin.model

class OrderItem (
    val id: String,
    val quantity: Int,
    val personalization: Personalization,
    val order: Order,
    val product: ProductWithVariant,
){
    fun getPrice(): Double{
        return (product.product.price + personalization.getPrice()) * quantity
    }
}