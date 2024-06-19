package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.dto.CustomerSummaryDto

class CustomerSummary(
    val id: String,
    val profilePic: Bitmap,
    val username: String
) {

    companion object {
        suspend fun fromDto(customerSummaryDto: CustomerSummaryDto): CustomerSummary{
            return CustomerSummary(
                id = customerSummaryDto.id,
                username = customerSummaryDto.username,
                profilePic = S3ImageDownloader.getImageForBucket(customerSummaryDto.profilePic)
            )
        }
    }
}