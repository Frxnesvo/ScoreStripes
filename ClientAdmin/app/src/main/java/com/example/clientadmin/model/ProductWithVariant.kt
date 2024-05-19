package com.example.clientadmin.model

import com.example.clientadmin.model.enumerator.Size

data class ProductWithVariant(
    val size: Size,
    val availability: Int,
)