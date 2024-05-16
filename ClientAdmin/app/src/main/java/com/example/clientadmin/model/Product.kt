package com.example.clientadmin.model

import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory


class Product (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val brand: String = "",
    val gender: Gender,
    val productCategory: ProductCategory,
    val pics: List<ProductPic?> = listOf(),
    val club: Club,
    val variants: ProductWithVariant? = null
){
    init {
        validateName(name)
        validateBrand(brand)
        validatePrice(price)
        validateDescription(description)
    }
    fun getPic1(): ProductPic?{
        return pics[0]
    }
    fun getPic2(): ProductPic?{
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