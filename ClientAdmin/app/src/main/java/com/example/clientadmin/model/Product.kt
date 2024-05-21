package com.example.clientadmin.model

import android.graphics.Bitmap
import android.net.Uri
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size


class Product (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val brand: String = "",
    val gender: Gender,
    val productCategory: ProductCategory,
    private val pics: List<Uri> = listOf(),
    val club: String,
    val variants: Map<Size, Int> = mapOf()
){
    init {
        require(validateName(name)) { "Invalid name: must be between 1 and 30 characters" }
        require(validatePrice(price)) { "Invalid price: must be greater than 0" }
        require(validateDescription(description)) { "Invalid description: must be between 1 and 50 characters" }
        require(validateBrand(brand)) { "Invalid brand: must be between 1 and 20 characters" }
    }
    fun getPic1(): Uri{
        return pics[0]
    }
    fun getPic2(): Uri{
        return pics[1]
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
    }
}