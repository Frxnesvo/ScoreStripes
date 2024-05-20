package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.impl.ClubApiServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ClubViewModel : ViewModel() {
    private val _clubNames = MutableStateFlow<List<String>>(emptyList())
    val clubNames: StateFlow<List<String>> = _clubNames

    private val clubApiService = ClubApiServiceImpl()

    init {
        fetchClubNames()
    }

    private fun fetchClubNames() {
        viewModelScope.launch {
            try {
                val response = clubApiService.getClubNames().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _clubNames.value = it
                    }
                } else {
                    handleApiError(response.message())
                }
            } catch (e: Exception) {
                handleApiError(e.message ?: "Unknown error")
            }
        }
    }

    fun addClub(context: Context, name: String, pic: Uri, league: String) {
        viewModelScope.launch {
            val multipart = ConverterUri.convert(context, pic, "image")
            if (multipart == null) {
                println("Errore nella conversione dell'Uri in MultipartBody.Part")
                return@launch
            }

            val clubRequestDto = ClubRequestDto(name, multipart, league)
            try {
                val response = clubApiService.createClub(clubRequestDto).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { club ->
                        _clubNames.value += club.name
                    }
                } else {
                    handleApiError(response.message())
                }
            } catch (e: Exception) {
                handleApiError(e.message ?: "Unknown error")
            }
        }
    }

    private fun handleApiError(response: String) {
        //TODO
        println("Errore nella chiamata API: $response")
    }
}
