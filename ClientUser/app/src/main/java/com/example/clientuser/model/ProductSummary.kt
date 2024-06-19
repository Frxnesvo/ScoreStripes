package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.dto.ProductSummaryDto
import com.example.clientuser.model.enumerator.Size

class ProductSummary(
    val id: String,
    val name: String,
    val picUrl: Bitmap,

    //TODO si posso rimuovere i campi qui sotto?
    val variants: Map<Size, Int>,
    val clubName: String,
    val leagueName: String,
) {
    companion object {
        suspend fun fromDto(productSummaryDto: ProductSummaryDto): ProductSummary{
            return ProductSummary(
                id = productSummaryDto.id,
                name = productSummaryDto.name,
                picUrl = S3ImageDownloader.getImageForBucket(productSummaryDto.picUrl),

                //TODO si posso rimuovere i campi qui sotto?
                clubName = productSummaryDto.clubName,
                leagueName = productSummaryDto.leagueName,
                variants = productSummaryDto.variants
            )
        }
    }
}