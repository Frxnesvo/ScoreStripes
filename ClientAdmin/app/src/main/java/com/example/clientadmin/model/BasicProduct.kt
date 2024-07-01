package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.BasicProductDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.utils.S3ImageDownloader

class BasicProduct(
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val gender: Gender,
    val pic: Bitmap,
    val club: String,
    val league: String
) {
    companion object {
        suspend fun fromDto(basicProductDto: BasicProductDto): BasicProduct {
            return BasicProduct(
                id = basicProductDto.id,
                name = basicProductDto.name,
                description = basicProductDto.description,
                brand = basicProductDto.brand,
                gender = basicProductDto.gender,
                pic = S3ImageDownloader.getImageFromBucket(basicProductDto.picUrl),
                club = basicProductDto.club,
                league = basicProductDto.league
            )
        }
    }
}