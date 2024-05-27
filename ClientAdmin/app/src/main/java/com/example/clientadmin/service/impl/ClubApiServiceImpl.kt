package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.ClubDto
import com.example.clientadmin.model.dto.ClubRequestDto
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.ClubApiService
import retrofit2.Call

class ClubApiServiceImpl: ClubApiService {
    override fun createClub(clubRequestDto: ClubRequestDto): Call<ClubDto> {
        return RetrofitHandler.getClubApi().createClub(clubRequestDto)
    }
    override fun getClubNames(): Call<List<String>> {
        return RetrofitHandler.getClubApi().getClubNames()
    }
}