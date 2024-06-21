package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.WishListVisibility
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WishListDto(
    val id: String,
    val ownerUsername: String,
    val items: List<WishlistItemDto>,
    val visibility: WishListVisibility
)
