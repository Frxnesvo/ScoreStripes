package com.example.clientadmin.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.viewmodels.formViewModel.ProductState


class Product (
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val gender: Gender,
    val productCategory: ProductCategory,
    private val pic1: Uri,
    private val pic2: Uri,
    val club: String,
    val variants: Map<Size, Int>
){
    init {
        require(validateName(name)) { "Invalid name: must be between 1 and 30 characters" }
        require(validatePrice(price)) { "Invalid price: must be greater than 0" }
        require(validateDescription(description)) { "Invalid description: must be between 1 and 50 characters" }
        require(validateBrand(brand)) { "Invalid brand: must be between 1 and 20 characters" }
    }

    fun createRequest(context: Context): ProductCreateRequestDto{
        try{
            val picMultipart1 = ConverterUri.convert(context, pic1, "pic")
            val picMultipart2 = ConverterUri.convert(context, pic2, "pic")

            return ProductCreateRequestDto(
                name = name,
                description = description,
                price = price,
                club = club,
                brand = brand,
                gender = gender,
                productCategory = productCategory,
                pic1 = picMultipart1!!,
                pic2 = picMultipart2!!,
                variants = variants
            )
        }
        catch (e: Exception) { throw e }
    }

    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 1..30
        }
        fun validatePrice(price: Double): Boolean{
            return price > 0
        }
        fun validateDescription(description: String): Boolean{
            return description.length in 1..50
        }
        fun validateBrand(brand: String): Boolean{
            return brand.length in 1..20
        }
        fun validatePic(brand: String): Boolean{
            return brand.length in 1..20
        }
    }
}