package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.League
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LeagueViewModel : ViewModel() {
    private val _leagues = MutableStateFlow<List<League>>(emptyList())
    val leagues = _leagues

    init { fetchLeagues() }

    private fun fetchLeagues(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.getLeagues().awaitResponse()
                if(response.isSuccessful) response.body()?.let {
                    _leagues.value = it.map { league -> League.fromDto(league) }
                }
                else println("Error during the get of leagues: ${response.message()}")
            }
        }
        catch (e : Exception){
            println("Exception during the get of league names: ${e.message}")
        }
    }

}