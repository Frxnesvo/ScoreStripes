package com.example.clientuser.model

import android.icu.text.DecimalFormat
import com.example.clientuser.model.dto.CartItemDto

data class CartItem(
    val id: String,
    val quantity: Int,
    val personalization: Personalization?,
    val productWithVariant: ProductWithVariant,
    val price: Double
) {
    companion object {
        suspend fun fromDto(cartItemDto: CartItemDto) : CartItem{
            return CartItem(
                id = cartItemDto.id,
                quantity = cartItemDto.quantity,
                personalization = cartItemDto.personalization,
                price = cartItemDto.price,
                productWithVariant = ProductWithVariant.fromDto(cartItemDto.product)
            )
        }
    }

}