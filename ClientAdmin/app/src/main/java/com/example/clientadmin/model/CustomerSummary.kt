package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.CustomerSummaryDto
import com.example.clientadmin.utils.S3ImageDownloader

class CustomerSummary (
    val id: String,
    val username: String,
    val pic: Bitmap
){
    companion object{
        suspend fun fromDto(customerSummaryDto: CustomerSummaryDto): CustomerSummary{
            return CustomerSummary(
                id = customerSummaryDto.id,
                username = customerSummaryDto.username,
                pic = S3ImageDownloader.getImageFromBucket(customerSummaryDto.profilePicUrl)
            )
        }
    }
}