package com.example.clientuser.model

import com.example.clientuser.model.Enum.Gender
import com.example.clientuser.model.Enum.ProductCategory


data class Product constructor(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val brand: String = "",
    val gender: Gender,
    val category: ProductCategory,
    val pics: ProductPic? = null,
    val club: Club,
    val variants: ProductWithVariant? = null
)