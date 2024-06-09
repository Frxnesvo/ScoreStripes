package com.example.clientuser.model

import com.example.clientuser.model.dto.WishListDto
import com.example.clientuser.model.dto.WishlistItemDto

class Wishlist(
    val id: String,
    val ownerUsername: String,
    val items: List<WishlistItem>
) {
    companion object{
        suspend fun fromDto(wishlistDto: WishListDto) : Wishlist{
            return Wishlist(
                id = wishlistDto.id,
                ownerUsername = wishlistDto.ownerUsername,
                items = wishlistDto.items.map { WishlistItem.fromDto(it) }
            )
        }
    }
}