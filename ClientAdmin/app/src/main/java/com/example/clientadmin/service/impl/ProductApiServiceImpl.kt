package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.model.ProductSummary
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.ProductApiService
import retrofit2.Call

class ProductApiServiceImpl: ProductApiService {
    override fun getProductsSummary(page: Int, size: Int, filters: Map<String, String?>): Call<List<ProductSummary>> {
        return RetrofitHandler.getProductApi().getProductsSummary(page, size, filters)
    }

    override fun getProductById(id: String): Call<ProductDto> {
        return RetrofitHandler.getProductApi().getProductById(id)
    }

    override fun createProduct(productCreateRequestDto: ProductCreateRequestDto): Call<ProductDto> {
        return RetrofitHandler.getProductApi().createProduct(productCreateRequestDto)
    }
}