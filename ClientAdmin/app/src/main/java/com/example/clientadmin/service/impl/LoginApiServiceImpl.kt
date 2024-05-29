package com.example.clientadmin.service.impl

import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.dto.AdminDto
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.service.interfaces.LoginApiService
import okhttp3.MultipartBody
import retrofit2.Call

class LoginApiServiceImpl: LoginApiService {
    override fun checkAdminLogin(token: String): Call<AdminDto> {
        return RetrofitHandler.loginApi.checkAdminLogin(token)
    }
    override fun adminRegister(token: String, userDto: AdminCreateRequestDto, imageProfile: MultipartBody.Part): Call<AdminDto> {
        return RetrofitHandler.loginApi.adminRegister(token, userDto, imageProfile)
    }
}