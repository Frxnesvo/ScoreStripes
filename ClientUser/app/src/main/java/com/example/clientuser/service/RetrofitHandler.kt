package com.example.clientuser.service

import com.example.clientuser.service.interfaces.CustomerApiService
import com.example.clientuser.service.interfaces.CartApiService
import com.example.clientuser.service.interfaces.ClubApiService
import com.example.clientuser.service.interfaces.LeagueApiService
import com.example.clientuser.service.interfaces.LoginApiService
import com.example.clientuser.service.interfaces.OrderApiService
import com.example.clientuser.service.interfaces.ProductApiService
import com.example.clientuser.service.interfaces.WishListApiService
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
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val loginApi: LoginApiService by lazy { retrofit.create(LoginApiService::class.java) }
    val leagueApi: LeagueApiService by lazy { retrofit.create(LeagueApiService::class.java) }
    val cartApi: CartApiService by lazy { retrofit.create(CartApiService::class.java) }
    val customerApi: CustomerApiService by lazy { retrofit.create(CustomerApiService::class.java) }
    val clubApi: ClubApiService by lazy { retrofit.create(ClubApiService::class.java)}
    val orderApi: OrderApiService by lazy { retrofit.create(OrderApiService::class.java)}
    val productApi: ProductApiService by lazy { retrofit.create(ProductApiService::class.java)}
    val wishListApi: WishListApiService by lazy { retrofit.create(WishListApiService::class.java)}
}