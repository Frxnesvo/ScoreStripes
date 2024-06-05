package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ClubViewModel: ViewModel() {
    private val _clubNames = getClubsName()
    val clubNames = _clubNames

    private val _clubs = getAllClubs()
    val clubs = _clubs

    private fun getClubsName(): Flow<List<String>> = flow {
        try {
            val response = RetrofitHandler.clubApi.getClubsName().awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(it)}
            else println(response.message())
        } catch (e: Exception) {
            println(e.message ?: "Unknown error")
        }
    }.flowOn(Dispatchers.IO)

    private fun getAllClubs() : Flow<List<ClubDto>> = flow {
        try{
            val response = RetrofitHandler.clubApi.getClubs().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error getting clubs: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception getting clubs: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}