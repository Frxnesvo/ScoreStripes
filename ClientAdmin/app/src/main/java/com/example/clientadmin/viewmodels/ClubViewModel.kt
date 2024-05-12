package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Club
import com.example.clientadmin.service.impl.ClubApiServiceImpl
import retrofit2.Retrofit

class ClubViewModel(): ViewModel() {
    private val clubApiServiceImpl = ClubApiServiceImpl()
    fun addClub(name: String, image: Bitmap?, league: String){
        //TODO
    }
    fun getClubsNames(): List<String>{
        return clubApiServiceImpl.getClubSNames()
    }
    fun updateClub(name: String, image: Bitmap){
        //TODO
    }
}