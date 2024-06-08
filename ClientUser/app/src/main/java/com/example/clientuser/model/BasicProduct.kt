package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.enumerator.Gender
import kotlinx.coroutines.runBlocking

class BasicProduct(
    val id: String,
    val name: String,
    val description: String,
    val brand: String,
    val gender: Gender,
    val pic: Bitmap,
    val club: String
) {
    companion object{
        fun fromDto(basicProductDto: BasicProductDto) : BasicProduct{
            val image = runBlocking {
                S3ImageDownloader.getImageFromPresignedUrl(basicProductDto.picUrl)
            } ?: Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888)

            return BasicProduct(
                id = basicProductDto.id,
                name = basicProductDto.name,
                description = basicProductDto.description,
                brand = basicProductDto.brand,
                gender = basicProductDto.gender,
                pic = image,
                club = basicProductDto.club
            )
        }

    }
}