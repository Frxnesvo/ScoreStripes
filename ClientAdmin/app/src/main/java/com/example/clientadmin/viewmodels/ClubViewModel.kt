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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ClubViewModel : ViewModel() {
    private val _clubNames = MutableStateFlow<List<String>>(emptyList())
    val clubNames: StateFlow<List<String>> = _clubNames

    private val clubApiService = ClubApiServiceImpl()

    init {
        fetchClubNames()
    }

    private fun fetchClubNames() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) { clubApiService.getClubNames() }
            if (response.isSuccessful) {
                response.body()?.let {
                    _clubNames.value = it
                }
            } else {
                handleApiError(response)
            }
        }
    }

    fun addClub(context: Context, name: String, pic: Uri, league: String) {
        viewModelScope.launch {
            val multipart = withContext(Dispatchers.IO) { ConverterUri.convert(context, pic, "image") }
            if (multipart == null) {
                println("Errore nella conversione dell'Uri in MultipartBody.Part")
                return@launch
            }

            val clubRequestDto = ClubRequestDto(name, multipart, league)
            val response = withContext(Dispatchers.IO) { clubApiService.createClub(clubRequestDto) }
            if (response.isSuccessful) {
                response.body()?.let { club -> _clubNames.value += club.name }
            } else {
                handleApiError(response)
            }
        }
    }

    private fun handleApiError(response: Response<*>) {
        //TODO Vedere come gestire l'errore

        println("Errore nella chiamata API: ${response.message()}")
    }
}
