package com.example.clientadmin.service

import com.example.clientadmin.service.interfaces.ClubApiService
import com.example.clientadmin.service.interfaces.CustomerApiService
import com.example.clientadmin.service.interfaces.LeagueApiService
import com.example.clientadmin.service.interfaces.LoginApiService
import com.example.clientadmin.service.interfaces.OrdersApiService
import com.example.clientadmin.service.interfaces.ProductApiService
import com.example.clientadmin.service.interfaces.ProductVariantApiService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHandler {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val moshi = Moshi.Builder()
        //.add(KotlinJsonAdapterFactory()) //dovrebbe non essere necessario usando code gen di moshi
        //.add(LocalDateAdapter())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val leagueApi: LeagueApiService by lazy { retrofit.create(LeagueApiService::class.java) }
    val clubApi: ClubApiService by lazy { retrofit.create(ClubApiService::class.java) }
    val customerApi: CustomerApiService by lazy { retrofit.create(CustomerApiService::class.java) }
    val productApi: ProductApiService by lazy { retrofit.create(ProductApiService::class.java) }
    val ordersApi: OrdersApiService by lazy { retrofit.create(OrdersApiService::class.java) }
    val productVariantApi: ProductVariantApiService by lazy { retrofit.create(ProductVariantApiService::class.java) }
    val loginApi: LoginApiService by lazy { retrofit.create(LoginApiService::class.java) }
}