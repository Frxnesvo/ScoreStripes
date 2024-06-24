package com.example.clientuser.utils

import com.example.clientuser.api.CustomerApiService
import com.example.clientuser.api.CartApiService
import com.example.clientuser.api.ClubApiService
import com.example.clientuser.api.LeagueApiService
import com.example.clientuser.api.LoginApiService
import com.example.clientuser.api.OrderApiService
import com.example.clientuser.api.ProductApiService
import com.example.clientuser.api.WishListApiService
import com.example.clientuser.authentication.TokenInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHandler {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(TokenInterceptor())
        .build()

    private val moshi = Moshi.Builder()
        .add(LocalDateAdapter)
        .add(AddressAdapter)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.33:8080")
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