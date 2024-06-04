package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse

class HomeViewModel : ViewModel() {
    private val _clubs : Flow<List<ClubDto>> = getAllClubs()
    val clubs : Flow<List<ClubDto>> = _clubs

    fun getMostSelledProduct(){
        TODO("manca il controller rest")
    }

    fun getAllLeagues(){
        TODO("manca il controller rest")
    }

    private fun getAllClubs() : Flow<List<ClubDto>> = flow {
        try{
            val response = RetrofitHandler.homeApi.getClubs().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error getting clubs ${response.message()}")
        }
        catch (e : Exception){
            println("Exception getting clubs: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}