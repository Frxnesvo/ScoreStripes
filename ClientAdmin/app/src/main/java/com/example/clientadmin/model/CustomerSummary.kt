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
    companion object{
        fun fromDto(customerSummaryDto: CustomerSummaryDto): CustomerSummary{
            return CustomerSummary(
                id = customerSummaryDto.id,
                username = customerSummaryDto.username,
                pic = S3ImageDownloader.getImageForBucket(customerSummaryDto.profilePicUrl)
            )
        }
    }
}