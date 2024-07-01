package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import com.example.clientadmin.utils.S3ImageDownloader

class Product (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val gender: Gender,
    val productCategory: ProductCategory,
    val pic1: Bitmap,
    val pic2: Bitmap,
    val club: String,
    val variants: Map<Size, Int>
){
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 3..40 && name.isNotBlank()
        }
        fun validatePrice(price: Double?): Boolean{
            return price != null && price > 0
        }
        fun validateDescription(description: String): Boolean{
            return description.length in 10..500 && description.isNotBlank()
        }
        fun validateBrand(brand: String): Boolean{
            return brand.length in 2..40 && brand.isNotBlank()
        }
        fun validatePic(pic: Bitmap?): Boolean{
            return pic != null
        }

        suspend fun fromDto(productDto: ProductDto): Product{
            val principal: String
            val secondary: String
            if(productDto.pics[0].principal){
                principal = productDto.pics[0].picUrl
                secondary = productDto.pics[1].picUrl
            }else{
                principal = productDto.pics[1].picUrl
                secondary = productDto.pics[0].picUrl
            }

            return Product(
                id = productDto.id,
                name = productDto.name,
                description = productDto.description,
                price = productDto.price,
                brand = productDto.brand,
                gender = productDto.gender,
                productCategory = productDto.category,
                pic1 = S3ImageDownloader.getImageFromBucket(principal),
                pic2 = S3ImageDownloader.getImageFromBucket(secondary),
                club = productDto.clubName,
                variants = productDto.variants.associate { it.size to it.availability }
            )
        }
    }
}