package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Club
import com.example.clientadmin.service.impl.ClubApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = clubApiService.getClubNames().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _clubNames.value = it }
                } else handleApiError(response.message())
            } catch (e: Exception) {
                handleApiError(e.message ?: "Unknown error")
            }
        }
    }

    fun addClub(context: Context, name: String, pic: Uri, league: String) {
        try {
            val clubRequestDto = Club(name, pic, league).request(context)

            CoroutineScope(Dispatchers.IO).launch {
                val response = clubApiService.createClub(clubRequestDto).awaitResponse()
                if (response.isSuccessful)
                    response.body()?.let { club -> _clubNames.value += club.name }
                else handleApiError(response.message())
            }
        }
        catch (e: Exception) {
            handleApiError(e.message ?: "Unknown error")
        }

    }

    private fun handleApiError(response: String) {
        println("Errore nella chiamata API: $response")
    }
}
