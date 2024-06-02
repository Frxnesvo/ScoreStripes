package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LoginViewModel: ViewModel() {
    private val _user = MutableStateFlow<Admin?>(null)
    val user: StateFlow<Admin?> = _user

    fun getAdminFromToken(token: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.loginApi.checkAdminLogin("Bearer $token").awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _user.value = Admin.fromDto(it) }
                } else {
                    println("Error checking admin login: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Exception checking admin login: ${e.message}")
            }
        }
    }

    fun register(token: String, adminCreateRequestDto: AdminCreateRequestDto, pic: Uri, context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.loginApi.adminRegister(
                    "Bearer $token",
                    adminCreateRequestDto.username,
                    adminCreateRequestDto.birthDate,
                    adminCreateRequestDto.gender,
                    ConverterUri.convert(context, pic, "profilePic")!!
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { _user.value = Admin.fromDto(it) }
                } else {
                    println("Error registering admin: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Exception registering admin: ${e.message}")
            }
        }
    }
}