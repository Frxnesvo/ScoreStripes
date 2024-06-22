package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.dto.ClubCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.api.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class ClubViewModel : ViewModel() {
    private val _clubNames = MutableStateFlow<List<String>>(emptyList())
    private val _clubs = MutableStateFlow<List<Club>>(emptyList())

    val clubs: StateFlow<List<Club>> = _clubs
    val clubNames: StateFlow<List<String>> = _clubNames

    private var filter: Map<String, String?> = emptyMap()

    private val _addError =  mutableStateOf("")
    val addError = _addError

    init { fetchClubs() }

    fun setFilter(filter: Map<String, String?>) {
        this.filter = filter
        _clubNames.value = emptyList()
        _clubs.value = emptyList()
        fetchClubs()
    }

    private fun fetchClubs() { //todo settare i filtri
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.getClubNames().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let {clubs ->
                        _clubs.value = clubs.map { Club.fromDto(it) }
                        _clubNames.value = clubs.map { it.name }
                    }
                } else println(response.message())
            }
        } catch (e: Exception) {
            println(e.message ?: "Unknown error")
        }
    }

    fun addClub(clubCreateRequestDto: ClubCreateRequestDto, pic: Bitmap): Boolean{
        try {
            Club(name = clubCreateRequestDto.name, league = clubCreateRequestDto.league, pic = pic)
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.createClub(
                    name = MultipartBody.Part.createFormData("name", clubCreateRequestDto.name),
                    league = MultipartBody.Part.createFormData("leagueName", clubCreateRequestDto.league),
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "pic")
                ).awaitResponse()

                if (response.isSuccessful)
                    response.body()?.let { club ->
                        _clubs.value += Club.fromDto(club)
                        _clubNames.value += club.name
                    }
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
