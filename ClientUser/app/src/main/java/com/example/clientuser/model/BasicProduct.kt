package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.model.enumerator.ProductCategory

class BasicProduct(
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val gender: Gender,
    val pic: Bitmap,
    val club: String,
    val category: ProductCategory
) {
    companion object{
        suspend fun fromDto(basicProductDto: BasicProductDto) : BasicProduct{

            return BasicProduct(
                id = basicProductDto.id,
                name = basicProductDto.name,
                description = basicProductDto.description,
                brand = basicProductDto.brand,
                gender = basicProductDto.gender,
                pic = S3ImageDownloader.getImageForBucket(basicProductDto.picUrl),
                club = basicProductDto.club,
                category = basicProductDto.category
            )
        }

    }
}