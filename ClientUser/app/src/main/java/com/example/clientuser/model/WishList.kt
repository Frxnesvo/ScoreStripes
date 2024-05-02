package com.example.clientuser.model

import com.example.clientuser.model.Enum.WishListVisibility

data class WishList(
    val id: String,
    val visibility: WishListVisibility,
    val owner: Customer,
    val items: List<WishListItem>,
    val accesses: List<WishListAccess>
)