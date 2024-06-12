package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.S3ImageDownloader
import com.example.clientuser.model.dto.CustomerSummaryDto
import kotlinx.coroutines.flow.first

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
                profilePic = S3ImageDownloader.download(customerSummaryDto.profilePic).first()
            )
        }
    }
}