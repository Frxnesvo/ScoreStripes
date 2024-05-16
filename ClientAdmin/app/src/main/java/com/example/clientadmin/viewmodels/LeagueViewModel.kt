package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientadmin.model.League
import com.example.clientadmin.service.impl.ClubApiServiceImpl
import com.example.clientadmin.service.impl.LeagueApiServiceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class LeagueViewModel(): ViewModel() {
    private val _list = MutableStateFlow<List<String>>(emptyList())
    val leaguesNames = _list

    private val leagueApiServiceImpl = LeagueApiServiceImpl()

    init{
        fetchLeaguesNames()
    }

    fun fetchLeaguesNames(){
        viewModelScope.launch {
            _list.value = leagueApiServiceImpl.getLeagueNames()
        }
    }

    fun addLeague(name: String, pic: Bitmap){
        viewModelScope.launch {
            val createdLeague = leagueApiServiceImpl.createLeague(name, pic)
            createdLeague?.let {
                league -> _list.value += league.name
            }
        }
    }

    fun updateLeague(name: String, image: Bitmap){
        //TODO
    }
}