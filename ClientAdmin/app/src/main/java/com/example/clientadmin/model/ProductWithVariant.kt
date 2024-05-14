package com.example.clientadmin.model

import com.example.clientadmin.model.enumerator.Size

class ProductWithVariant(
    val id: String,
    val size: Size,
    val availability: Int,
    val product: Product
)