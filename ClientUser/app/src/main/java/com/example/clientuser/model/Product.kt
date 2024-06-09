package com.example.clientuser.model

import android.graphics.Bitmap
import android.net.Uri
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.Size
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

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
        suspend fun fromDto(productDto: ProductDto): Product {
            return Product(
                id = productDto.id,
                name = productDto.name,
                description = productDto.description,
                price = productDto.price,
                brand = productDto.brand,
                gender = productDto.gender,
                productCategory = productDto.productCategory,
                pic1 = S3ImageDownloader.download(productDto.pics[0]).first(),
                pic2 = S3ImageDownloader.download(productDto.pics[1]).first(),
                club = productDto.clubName,
                variants = productDto.variants.associate { it.size to it.availability }
            )
        }
    }
}