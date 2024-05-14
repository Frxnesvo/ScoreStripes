package com.example.clientadmin.service.impl

import com.example.clientadmin.service.interfaces.ClubApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ClubApiServiceImpl{
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val clubApi = retrofit.create(ClubApiService::class.java)

    fun getClubSNames(): List<String>{
        val call = clubApi.getClubNames()
        var names = listOf<String>()
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    names = response.body()!!
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                println("Error")
                //gestire l'errore
            }
        })
        return names
    }
    fun addClub(name: String, image: String, league: String){

    }
}