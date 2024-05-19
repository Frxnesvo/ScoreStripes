package com.example.clientadmin.model.dto

import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import okhttp3.MultipartBody

data class ProductCreateRequestDto (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val club: String,
    val brand: String,
    val gender: Gender,
    val productCategory: ProductCategory,
    val pic1: MultipartBody.Part,
    val pic2: MultipartBody.Part,
    val variants: Map<Size, Int>
)