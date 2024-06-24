package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.S3ImageDownloader
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class CustomerSummary (
    val id: String,
    val username: String,
    val pic: Bitmap
){
    fun toQueryString(): String {
        val picBase64 = ConverterBitmap.bitmapToBase64(pic)
        val encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString())
        val encodedId = URLEncoder.encode(id, StandardCharsets.UTF_8.toString())
        val encodedPic = URLEncoder.encode(picBase64, StandardCharsets.UTF_8.toString())
        return "id=$encodedId&username=$encodedUsername&pic=$encodedPic"
    }
    companion object{
        fun fromDto(customerSummaryDto: CustomerSummaryDto): CustomerSummary{
            return CustomerSummary(
                id = customerSummaryDto.id,
                username = customerSummaryDto.username,
                pic = S3ImageDownloader.getImageForBucket(customerSummaryDto.profilePicUrl)
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