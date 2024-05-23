package com.example.clientadmin.model.dto

import android.net.Uri
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory

data class ProductDto (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val gender: Gender,
    val productCategory: ProductCategory,
    val pics: List<Uri>,
    val clubName: String,
    val variants: List<ProductWithVariantAvailabilityDto>
)