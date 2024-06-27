package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.League
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
        else fetchLeagues()
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

    fun addLeague(name:String, pic: Bitmap): Boolean{
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.createLeague(
                    name = MultipartBody.Part.createFormData("name", name),
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "pic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { league ->
                        _leagues.value += League.fromDto(league)
                    }
                    ToastManager.show("League created successfully")
                }
                else ToastManager.show("Error creating league")
            }
            true
        } catch (e: Exception) { false }
    }

    fun updateLeague(name: String, pic: Bitmap): Boolean{
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.updateLeague(
                    name = name,
                    pic = ConverterBitmap.convert(bitmap = pic, fieldName = "pic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { league ->
                        _leagues.value = _leagues.value.map {
                            if (it.name == league.name) League.fromDto(league)
                            else it
                        }
                    }
                    ToastManager.show("League updated successfully")
                }
                else ToastManager.show("Error updating league")
            }
            true
        } catch (e: Exception) { false }
    }
}