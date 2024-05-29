package com.example.clientadmin.model

import android.net.Uri
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size

class Product (
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val gender: Gender,
    val productCategory: ProductCategory,
    val pic1: Uri,
    val pic2: Uri,
    val club: String,
    val variants: Map<Size, Int>
){
    init {
        require(validateName(name)) { "Invalid name: must be between 1 and 30 characters" }
        require(validatePrice(price)) { "Invalid price: must be greater than 0" }
        require(validateDescription(description)) { "Invalid description: must be between 1 and 50 characters" }
        require(validateBrand(brand)) { "Invalid brand: must be between 1 and 20 characters" }
        require(validatePic(pic1)) { "Invalid brand: must be between 1 and 20 characters" }
        require(validatePic(pic2)) { "Invalid brand: must be between 1 and 20 characters" }
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
        fun validatePic(pic: Uri): Boolean{
            return pic != Uri.EMPTY
        }

        fun fromDto(productDto: ProductDto): Product{
            return Product(
                name = productDto.name,
                description = productDto.description,
                price = productDto.price,
                brand = productDto.brand,
                gender = productDto.gender,
                productCategory = productDto.productCategory,
                pic1 = Uri.EMPTY,//productDto.pics[0]
                pic2 = Uri.EMPTY,//productDto.pics[1]
                club = productDto.clubName,
                variants = productDto.variants.associate { it.size to it.availability }
            )
        }
    }
}