package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.FilterBuilder
import com.example.clientadmin.model.League
import com.example.clientadmin.model.dto.LeagueCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class LeagueViewModel: ViewModel() {
    private val _leaguesNames = MutableStateFlow<List<String>>(emptyList())
    private val _leagues = MutableStateFlow<List<League>>(emptyList())

    val leaguesNames: StateFlow<List<String>> = _leaguesNames
    val leagues: StateFlow<List<League>> = _leagues

    private var filter: Map<String, String?> = FilterBuilder().build()

    private val _addError =  mutableStateOf("")
    val addError = _addError

    init{ fetchLeagues() }

    fun setFilter(filter: Map<String, String?>) {
        this.filter = filter
        _leaguesNames.value = emptyList()
        _leagues.value = emptyList()
        fetchLeagues()
    }

    private fun fetchLeagues(){ //todo settare i filtri
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.leagueApi.getLeagues().awaitResponse()
                if (response.isSuccessful) response.body()?.let {leagues ->
                    _leagues.value = leagues.map { League.fromDto(it) }
                    _leaguesNames.value = leagues.map { it.name }
                }
                else println(response.message())
            } catch (e: Exception) {
                println(e.message ?: "Unknown error")
            }
        }
    }

    fun addLeague(leagueCreateRequestDto: LeagueCreateRequestDto, pic: Bitmap): Boolean{
        try {
            League(name = leagueCreateRequestDto.name, pic = pic)
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.createLeague(
                    name = MultipartBody.Part.createFormData("name", leagueCreateRequestDto.name),
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "pic")
                ).awaitResponse()

                if (response.isSuccessful)
                    response.body()?.let { league ->
                        _leagues.value += League.fromDto(league)
                        _leaguesNames.value += league.name
                    }
                else println(response.message())
            }
            return true
        } catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            return false
        }
    }
}