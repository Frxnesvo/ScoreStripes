package com.example.clientadmin.service.impl

import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.ClubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ClubApiServiceImpl: ClubApiService {
    override fun createClub(clubRequestDto: ClubRequestDto): Response<ClubDto> {
        return RetrofitHandler.getClubApi().createClub(clubRequestDto)
    }
    override fun getClubNames(): Response<List<String>> {
        return RetrofitHandler.getClubApi().getClubNames()
    }

}