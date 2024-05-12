package com.example.clientadmin.model


class Cart(
    val id: String,
    val cartItems: List<CartItem>
) {
    fun size(): Int{
        return this.cartItems.size
    }

    fun totalPrice(): Double{
        return cartItems.stream().mapToDouble { it.getPrice() }.sum()
    }
}