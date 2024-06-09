package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.enumerator.Gender
import kotlinx.coroutines.flow.first
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
        suspend fun fromDto(basicProductDto: BasicProductDto) : BasicProduct{

            return BasicProduct(
                id = basicProductDto.id,
                name = basicProductDto.name,
                description = basicProductDto.description,
                brand = basicProductDto.brand,
                gender = basicProductDto.gender,
                pic = S3ImageDownloader.download(basicProductDto.picUrl).first(),
                club = basicProductDto.club
            )
        }

    }
}