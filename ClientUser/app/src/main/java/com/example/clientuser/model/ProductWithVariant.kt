package com.example.clientuser.model

import com.example.clientuser.model.Enum.Size

class ProductWithVariant(
    val id: String,
    val size: Size,
    val availability: Int,
    val product: Product
)