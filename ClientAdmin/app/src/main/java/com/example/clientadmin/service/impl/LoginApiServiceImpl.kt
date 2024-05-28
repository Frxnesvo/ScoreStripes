package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.dto.AdminDto
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.LoginApiService
import retrofit2.Call

class LoginApiServiceImpl: LoginApiService {
    override fun checkAdminLogin(token: String): Call<AdminDto> {
        return RetrofitHandler.getLoginApi().checkAdminLogin(token)
    }
    override fun adminRegister(adminCreateRequestDto: AdminCreateRequestDto): Call<AdminDto> {
        return RetrofitHandler.getLoginApi().adminRegister(adminCreateRequestDto)
    }
}