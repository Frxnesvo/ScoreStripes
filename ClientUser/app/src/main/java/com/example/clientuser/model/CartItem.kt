package com.example.clientuser.model

class CartItem(
    val id: String,
    val quantity: Int,
    val personalization: Personalization,
    val cart: Cart,
    val product: ProductWithVariant,
) {
    fun getPrice(): Double{
        return (product.product.price + personalization.getPrice()) * quantity
    }
}