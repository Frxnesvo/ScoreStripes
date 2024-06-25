package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.Size

class Product (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val brand: String = "",
    val gender: Gender = Gender.MALE,
    val productCategory: ProductCategory = ProductCategory.JERSEY,
    val pic1: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val pic2: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val club: String = "",
    val variants: Map<Size, Int> = emptyMap()
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
                productCategory = productDto.category,
                pic1 = S3ImageDownloader.getImageForBucket(productDto.pics[0].picUrl),
                pic2 = S3ImageDownloader.getImageForBucket(productDto.pics[1].picUrl),
                club = productDto.clubName,
                variants = productDto.variants.associate { it.size to it.availability }
            )
        }
    }
}