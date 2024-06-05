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

class LeagueViewModel : ViewModel() {
    private val _leagues = getAllLeagues()
    val leagues = _leagues

    private fun getAllLeagues() : Flow<List<LeagueDto>> = flow<List<LeagueDto>> {
        try{
            val response = RetrofitHandler.leagueApi.getLeagues().awaitResponse()
            if(response.isSuccessful) response.body()?.let {emit(it)}
            else println("Error getting leagues: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception getting league: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

}