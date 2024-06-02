package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.dto.LeagueCreateRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LeagueViewModel: ViewModel() {
    private val _leaguesNames = MutableStateFlow<List<String>>(emptyList())
    val leaguesNames = _leaguesNames

    init{
        fetchLeaguesNames()
    }

    private fun fetchLeaguesNames(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.leagueApi.getLeagueNames().awaitResponse()
                if (response.isSuccessful)
                    response.body()?.let { _leaguesNames.value = it }
                else
                    handleApiError(response.message())
            } catch (e: Exception) {
                handleApiError(e.message ?: "Unknown error")
            }
        }
    }

    fun addLeague(context: Context, name: String, pic: Uri){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.createLeague(
                    name = LeagueCreateRequestDto(name).name,
                    pic = ConverterUri.convert(context, pic, "pic")!!
                ).awaitResponse()
                if (response.isSuccessful)
                    response.body()?.let { club -> _leaguesNames.value += club.name }
                else handleApiError(response.message())
            }
        } catch (e: Exception) {
            handleApiError(e.message ?: "Unknown error")
        }
    }

    private fun handleApiError(response: String) {
        println("Error in API call: $response")
    }
}