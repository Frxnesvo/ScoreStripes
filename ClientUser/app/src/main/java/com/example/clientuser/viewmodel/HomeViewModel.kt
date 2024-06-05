package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.model.dto.LeagueDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse

class HomeViewModel : ViewModel() {
    private val _clubs : Flow<List<ClubDto>> = getAllClubs()
    val clubs : Flow<List<ClubDto>> = _clubs

    private val _leagues : Flow<List<LeagueDto>> = getAllLeagues()
    val leagues : Flow<List<ClubDto>> = _clubs

    fun getMostSelledProduct(){
        TODO("manca il controller rest")
    }

    fun getAllLeagues() : Flow<List<LeagueDto>> = flow<List<LeagueDto>> {
        try{
            val response = RetrofitHandler.homeApi.getLeagues().awaitResponse()
            if(response.isSuccessful) response.body()?.let {emit(it)}
            else println("Error getting leagues: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception getting league: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getAllClubs() : Flow<List<ClubDto>> = flow {
        try{
            val response = RetrofitHandler.homeApi.getClubs().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error getting clubs: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception getting clubs: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)


}