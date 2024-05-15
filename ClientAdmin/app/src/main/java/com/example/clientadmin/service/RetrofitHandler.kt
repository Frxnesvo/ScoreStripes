package com.example.clientadmin.service

import com.example.clientadmin.service.interfaces.ClubApiService
import com.example.clientadmin.service.interfaces.LeagueApiService
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

    fun getLeagueApi(): LeagueApiService {
        return leagueApi
    }

    fun getClubApi(): ClubApiService {
        return clubApi
    }
}