package com.example.clientadmin.service.impl

import com.example.clientadmin.service.interfaces.ClubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ClubApiServiceImpl{
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val clubApi = retrofit.create(ClubApiService::class.java)

    suspend fun getClubSNames(): List<String> {
        println("CIAO DEBUG")
        return withContext(Dispatchers.IO) {
            try {
                val response = clubApi.getClubNames() // Chiamata API Retrofit
                if (response.isSuccessful) {
                    response.body() ?: emptyList() // Restituisci la lista dei nomi dei club se la risposta è riuscita, altrimenti restituisci una lista vuota
                } else {
                    emptyList() // Restituisci una lista vuota se la risposta non è riuscita
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList() // Restituisci una lista vuota in caso di eccezione
            }
        }
    }


}