package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.dto.ClubCreateRequestDto
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

class ClubViewModel : ViewModel() {
    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs

    init { fetchClubs() }

    fun setFilter(filter: Map<String, String>?) {
        if (filter != null) {
            _clubs.value = _clubs.value.filter {
                it.name.contains(filter["name"] ?: "", ignoreCase = true) &&
                it.league.contains(filter["league"] ?: "", ignoreCase = true)
            }
        } else fetchClubs()
    }

    private fun fetchClubs() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.getClubNames().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let {clubs ->
                        _clubs.value = clubs.map { Club.fromDto(it) }
                    }
                } else println(response.message())
            }
        } catch (e: Exception) {
            println(e.message ?: "Unknown error")
        }
    }

    fun addClub(clubCreateRequestDto: ClubCreateRequestDto, pic: Bitmap): Boolean{
        var returnValue = false
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.createClub(
                    name = MultipartBody.Part.createFormData("name", clubCreateRequestDto.name),
                    league = MultipartBody.Part.createFormData("leagueName", clubCreateRequestDto.league),
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "pic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { club ->
                        _clubs.value += Club.fromDto(club)
                    }
                    returnValue = true
                }
                else ToastManager.show(response.message())
            }
            return returnValue
        }
        catch (e: Exception) {
            return returnValue
        }
    }
}
