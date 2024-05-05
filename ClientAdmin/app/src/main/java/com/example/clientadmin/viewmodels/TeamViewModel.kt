package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TeamViewModel(): ViewModel() {
    fun addTeam(name: String, image: Bitmap?, league: String){
        //TODO
    }
    fun getTeamsOf(league: String){
        //TODO
    }
    fun updateTeam(name: String, image: Bitmap){
        //TODO
    }
}