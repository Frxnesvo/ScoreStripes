package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.dto.ClubCreateRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ClubViewModel : ViewModel() {
    private val _clubNames = MutableStateFlow<List<String>>(emptyList())
    val clubNames: StateFlow<List<String>> = _clubNames

    private val _addError =  mutableStateOf("")
    val addError = _addError

    init {
        fetchClubNames()
    }

    private fun fetchClubNames() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.getClubNames().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _clubNames.value = it }
                } else println(response.message())
            }
        } catch (e: Exception) {
            println(e.message ?: "Unknown error")
        }
    }

    fun addClub(context: Context, clubCreateRequestDto: ClubCreateRequestDto, pic: Uri):Boolean {
        try {
            Club(name = clubCreateRequestDto.name, league = clubCreateRequestDto.league, image = pic)
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.createClub(
                    name = clubCreateRequestDto.name,
                    league = clubCreateRequestDto.league,
                    pic = ConverterUri.convert(context, pic, "picLeague")!!
                ).awaitResponse()

                if (response.isSuccessful)
                    response.body()?.let { club -> _clubNames.value += club.name }
                else println(response.message())
            }
            return true
        }
        catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            return false
        }

    }
}
