package com.example.clientadmin.model

import com.example.clientadmin.model.enumerator.WishListVisibility

data class WishList(
    val id: String,
    val visibility: WishListVisibility,
    val owner: Customer,
    val items: List<WishListItem>,
    val accesses: List<WishListAccess>
)