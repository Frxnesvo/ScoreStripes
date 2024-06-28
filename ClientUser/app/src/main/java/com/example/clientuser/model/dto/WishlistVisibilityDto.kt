package com.example.clientuser.model.dto

import com.example.clientuser.model.enumerator.WishListVisibility
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WishlistVisibilityDto(
    val visibility: WishListVisibility
)
