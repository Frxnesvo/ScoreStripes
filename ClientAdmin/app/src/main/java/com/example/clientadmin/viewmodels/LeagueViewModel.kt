package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.League
import com.example.clientadmin.model.dto.LeagueCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.utils.ToastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class LeagueViewModel: ViewModel() {
    private val _leagues =  MutableStateFlow<List<League>>(emptyList())
    val leagues: StateFlow<List<League>> = _leagues

    init{ fetchLeagues() }

    fun setFilter(filter: Map<String, String>?) {
        if (filter != null)
            _leagues.value = _leagues.value.filter {
                it.name.contains(filter["name"] ?: "", ignoreCase = true)
            }
        else fetchLeagues() //todo preferisco rifare la chamata rispetto a tenere in memoria
    }

    private fun fetchLeagues(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.leagueApi.getLeagues().awaitResponse()
                if (response.isSuccessful) response.body()?.let {leagues ->
                    _leagues.value = leagues.map { League.fromDto(it) }
                }
                else println(response.message())
            } catch (e: Exception) {
                println(e.message ?: "Unknown error")
            }
        }
    }

    fun addLeague(leagueCreateRequestDto: LeagueCreateRequestDto, pic: Bitmap): Boolean{
        var returnValue = false
        try {
            League(name = leagueCreateRequestDto.name, pic = pic)
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.createLeague(
                    name = MultipartBody.Part.createFormData("name", leagueCreateRequestDto.name),
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "pic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { league ->
                        _leagues.value += League.fromDto(league)
                    }
                    ToastManager.show("League created successfully")
                    returnValue = true
                }
                else ToastManager.show("Error creating league")
            }
            return returnValue
        } catch (e: Exception) {
            println("Exception creating league: ${e.message}")
            return returnValue
        }
    }
}