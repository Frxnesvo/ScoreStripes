package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse

class ClubViewModel: ViewModel() {
    private val _clubNames = getClubsName()
    val clubNames = _clubNames

    private fun getClubsName(): Flow<List<String>> = flow {
        try {
            val response = RetrofitHandler.clubApi.getClubsName().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it)}
            else println(response.message())
        } catch (e: Exception) {
            println(e.message ?: "Unknown error")
        }
    }.flowOn(Dispatchers.IO)

}