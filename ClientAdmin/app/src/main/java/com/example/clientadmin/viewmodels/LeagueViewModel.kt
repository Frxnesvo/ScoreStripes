package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LeagueViewModel(): ViewModel() {
    private val _list : Flow<List<League>> = flowOf() //TODO allLeague
    val leagues = _list

    fun addLeague(name: String, image: Bitmap?){
        //TODO
    }
    fun updateLeague(name: String, image: Bitmap){
        //TODO
    }
}