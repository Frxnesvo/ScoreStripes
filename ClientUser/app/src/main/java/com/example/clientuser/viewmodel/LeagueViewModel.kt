package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.League
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse

class LeagueViewModel : ViewModel() {
    private val _leagues = getAllLeagues()
    val leagues = _leagues

    private val _leaguesNames = fetchLeaguesNames()
    val leaguesNames = _leaguesNames

    private fun getAllLeagues() : Flow<List<League>> = flow {
        try{
            val response = RetrofitHandler.leagueApi.getLeagues().awaitResponse()
            if(response.isSuccessful) response.body()?.let {
                val newList = it.map { League.fromDto(it) }
                emit(newList)
            }
            else println("Error getting leagues: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception getting league: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    private fun fetchLeaguesNames() : Flow<List<String>> = flow {
        try{
            val response = RetrofitHandler.leagueApi.getLeaguesNames().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error during the get of league names: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the get of league names: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

}