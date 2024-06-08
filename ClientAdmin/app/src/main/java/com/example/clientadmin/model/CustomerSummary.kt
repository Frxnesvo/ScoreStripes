package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.S3ImageDownloader
import kotlinx.coroutines.flow.first

class CustomerSummary (
    val id: String,
    val username: String,
    val pic: Bitmap
){

    fun toQueryString(): String {
        val picBase64 = ConverterBitmap.bitmapToBase64(pic)
        return "id=$id&username=$username&pic=$picBase64"
    }
    companion object{
        suspend fun fromDto(customerSummaryDto: CustomerSummaryDto): CustomerSummary{
            return CustomerSummary(
                id = customerSummaryDto.id,
                username = customerSummaryDto.username,
                pic = S3ImageDownloader.download(customerSummaryDto.picUrl).first()
            )
        }
        fun fromQueryString(queryString: String): CustomerSummary {
            val params = queryString.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val id = params["id"] ?: throw IllegalArgumentException("Id is missing")
            val username = params["username"] ?: throw IllegalArgumentException("Username is missing")
            val picBase64 = params["pic"] ?: throw IllegalArgumentException("Pic is missing")
            val pic = ConverterBitmap.base64ToBitmap(picBase64)

            return CustomerSummary(id, username, pic)
        }
    }
}