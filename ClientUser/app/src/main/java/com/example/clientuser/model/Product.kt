package com.example.clientuser.model

import android.net.Uri
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.Size

class Product (
    val id: String,
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
    companion object{
        fun fromDto(productDto: ProductDto): Product {
            return Product(
                id = productDto.id,
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