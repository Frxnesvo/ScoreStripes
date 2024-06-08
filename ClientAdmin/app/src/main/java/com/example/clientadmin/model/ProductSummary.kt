package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.ProductSummaryDto
import com.example.clientadmin.model.enumerator.Size
import com.example.clientadmin.utils.S3ImageDownloader
import kotlinx.coroutines.flow.first

class ProductSummary(
    val id: String,
    val name: String,
    val club: String,
    val league: String,
    val pic: Bitmap,
    val variants: Map<Size, Int>
) {
    companion object{
        suspend fun fromDto(productSummaryDto: ProductSummaryDto): ProductSummary{
            return ProductSummary(
                id = productSummaryDto.id,
                name = productSummaryDto.name,
                club = productSummaryDto.clubName,
                league = productSummaryDto.leagueName,
                pic = S3ImageDownloader.download(productSummaryDto.picUrl).first(),
                variants = productSummaryDto.variants
            )
        }
    }
}