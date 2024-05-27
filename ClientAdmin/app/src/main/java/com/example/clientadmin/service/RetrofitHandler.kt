package com.example.clientadmin.service

import com.example.clientadmin.service.interfaces.ClubApiService
import com.example.clientadmin.service.interfaces.CustomerApiService
import com.example.clientadmin.service.interfaces.LeagueApiService
import com.example.clientadmin.service.interfaces.LoginApiService
import com.example.clientadmin.service.interfaces.OrdersApiService
import com.example.clientadmin.service.interfaces.ProductApiService
import com.example.clientadmin.service.interfaces.ProductVariantApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHandler {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val leagueApi = retrofit.create(LeagueApiService::class.java)
    private val clubApi = retrofit.create(ClubApiService::class.java)
    private  val customerApi = retrofit.create(CustomerApiService::class.java)
    private val productApi = retrofit.create(ProductApiService::class.java)
    private val ordersApi = retrofit.create(OrdersApiService::class.java)
    private val productVariantApi = retrofit.create(ProductVariantApiService::class.java)
    private val loginApi = retrofit.create(LoginApiService::class.java)

    fun getLoginApi(): LoginApiService {
        return loginApi
    }
    fun getLeagueApi(): LeagueApiService {
        return leagueApi
    }
    fun getClubApi(): ClubApiService {
        return clubApi
    }
    fun getCustomerApi(): CustomerApiService {
        return customerApi
    }
    fun getProductApi(): ProductApiService {
        return productApi
    }
    fun getOrdersApi(): OrdersApiService {
        return ordersApi
    }
    fun getProductVariantApi() : ProductVariantApiService {
        return productVariantApi
    }
}