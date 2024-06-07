package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.League
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

    private val _addError =  mutableStateOf("")
    val addError = _addError

    init{
        fetchLeaguesNames()
    }

    private fun fetchLeaguesNames(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.leagueApi.getLeagueNames().awaitResponse()
                if (response.isSuccessful) response.body()?.let { _leaguesNames.value = it }
                else println(response.message())
            } catch (e: Exception) {
                println(e.message ?: "Unknown error")
            }
        }
    }

    fun addLeague(context: Context, leagueCreateRequestDto: LeagueCreateRequestDto, pic: Uri): Boolean{
        try {
            League(name = leagueCreateRequestDto.name, image = pic)
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.createLeague(
                    name = leagueCreateRequestDto.name,
                    pic = ConverterUri.convert(context, pic, "pic")!!
                ).awaitResponse()

                if (response.isSuccessful)
                    response.body()?.let { club -> _leaguesNames.value += club.name }
                else println(response.message())
            }
            return true
        } catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            return false
        }
    }

}