package com.example.clientadmin.model

data class WishListAccess(
    val id: String,
    val wishlist: WishList,
    val guest: Customer
)