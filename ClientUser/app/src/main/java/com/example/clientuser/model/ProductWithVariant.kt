package com.example.clientuser.model

import com.example.clientuser.model.dto.BasicProductDto
import com.example.clientuser.model.dto.ProductWithVariantDto
import com.example.clientuser.model.enumerator.Size

class ProductWithVariant(
    val id: String,
    val size: Size,
    val product: BasicProduct
) {
    companion object{
        suspend fun fromDto(productWithVariantDto: ProductWithVariantDto) : ProductWithVariant{
            return ProductWithVariant(
                id = productWithVariantDto.id,
                size = productWithVariantDto.size,
                product = BasicProduct.fromDto(productWithVariantDto.product)
            )
        }
    }
}