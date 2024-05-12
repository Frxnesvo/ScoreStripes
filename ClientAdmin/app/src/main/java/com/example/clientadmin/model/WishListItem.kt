package com.example.clientadmin.model

import java.time.LocalDate

data class WishListItem(
    val id: String,
    val dateAdded: LocalDate,
    val wishlist: WishList,
    val product: Product
)