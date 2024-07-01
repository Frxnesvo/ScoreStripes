package com.example.clientuser.model

import com.example.clientuser.model.dto.WishListDto
import com.example.clientuser.model.enumerator.WishListVisibility

data class Wishlist(
    val id: String = "",
    val ownerUsername: String = "",
    val items: List<WishlistItem> = emptyList(),
    val visibility: WishListVisibility = WishListVisibility.PRIVATE
) {
    companion object{
        suspend fun fromDto(wishlistDto: WishListDto) : Wishlist{
            return Wishlist(
                id = wishlistDto.id,
                items = wishlistDto.items.map { WishlistItem.fromDto(it) },
                visibility = wishlistDto.visibility,
                ownerUsername = wishlistDto.ownerUsername
            )
        }
    }
}