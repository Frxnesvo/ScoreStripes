package com.example.clientadmin.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.League
import com.example.clientadmin.model.dto.LeagueRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.impl.LeagueApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LeagueViewModel: ViewModel() {
    private val _leaguesNames = MutableStateFlow<List<String>>(emptyList())
    val leaguesNames = _leaguesNames

    private val leagueApiService = LeagueApiServiceImpl()

    init{
        fetchLeaguesNames()
    }

    private fun fetchLeaguesNames(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = leagueApiService.getLeagueNames().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _leaguesNames.value = it
                    }
                } else {
                    handleApiError(response.message())
                }
            } catch (e: Exception) {
                handleApiError(e.message ?: "Unknown error")
            }
        }
    }

    fun addLeague(context: Context, name: String, pic: Uri){
        try {
            val leagueRequestDto = League(name, pic).request(context)

            CoroutineScope(Dispatchers.IO).launch {
                val response = leagueApiService.createLeague(leagueRequestDto).awaitResponse()
                if (response.isSuccessful)
                    response.body()?.let { club -> _leaguesNames.value += club.name }
                else handleApiError(response.message())
            }
        } catch (e: Exception) {
            handleApiError(e.message ?: "Unknown error")
        }
    }

    private fun handleApiError(response: String) {
        //TODO
        println("Errore nella chiamata API: $response")
    }
}