package com.example.clientuser.model

data class WishListAccess(
    val id: String,
    val wishist: WishList,
    val guest: Customer
)