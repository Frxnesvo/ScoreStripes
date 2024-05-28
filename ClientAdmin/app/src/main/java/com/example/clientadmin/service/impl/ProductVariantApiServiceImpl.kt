package com.example.clientadmin.service.impl

import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.ProductVariantApiService
import retrofit2.Call

class ProductVariantApiServiceImpl: ProductVariantApiService {
    override fun countVariantsOutOfStock(): Call<Int> {
        return RetrofitHandler.productVariantApi.countVariantsOutOfStock()
    }
}