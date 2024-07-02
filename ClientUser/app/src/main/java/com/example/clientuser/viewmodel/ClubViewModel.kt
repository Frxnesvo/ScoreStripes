package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ClubViewModel: ViewModel() {
    private val _clubNames = MutableStateFlow<List<String>>(emptyList())
    val clubNames = _clubNames

    init {
        fetchClubsName()
    }

    private fun fetchClubsName() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.clubApi.getClubsName().awaitResponse()
                if (response.isSuccessful) response.body()?.let { _clubNames.value = it}
                else println(response.message())
            }
        } catch (e: Exception) {
            println(e.message ?: "Unknown error")
        }
    }

}