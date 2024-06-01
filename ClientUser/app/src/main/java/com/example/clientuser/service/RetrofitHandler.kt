package com.example.clientuser.service

import com.example.clientuser.service.interfaces.LoginApiService
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
        //.add(LocalDateAdapter()) // non so se è ancora necessario, per il momento lascio
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val loginApi: LoginApiService by lazy { retrofit.create(LoginApiService::class.java) }
}