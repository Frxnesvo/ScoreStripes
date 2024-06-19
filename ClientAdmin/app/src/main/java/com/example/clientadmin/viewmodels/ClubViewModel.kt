package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.dto.ClubCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
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

    fun addClub(clubCreateRequestDto: ClubCreateRequestDto, pic: Bitmap): Boolean{
        try {
            Club(name = clubCreateRequestDto.name, league = clubCreateRequestDto.league, image = pic)
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.createClub(
                    name = clubCreateRequestDto.name,
                    league = clubCreateRequestDto.league,
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "picLeague")
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

    fun setFilter(filter: Map<String, String?>) {
        //TODO
    }
}
