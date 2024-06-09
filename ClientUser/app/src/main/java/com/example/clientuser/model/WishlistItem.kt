package com.example.clientuser.model

import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.dto.WishlistItemDto
import java.time.LocalDate

class WishlistItem(
    val id: String,
    val dateAdded: LocalDate,
    val product: Product
) {
    companion object{
        suspend fun fromDto(wishlistItemDto: WishlistItemDto): WishlistItem{
            return WishlistItem(
                id = wishlistItemDto.id,
                dateAdded = wishlistItemDto.dateAdded,
                product = Product.fromDto(wishlistItemDto.product)
            )
        }
    }
}