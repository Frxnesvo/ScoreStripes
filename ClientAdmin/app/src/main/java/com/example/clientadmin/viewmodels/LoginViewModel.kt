package com.example.clientadmin.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.service.Converter
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import com.example.clientadmin.viewmodels.formViewModel.AdminState
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

    fun register(token: String, adminState: AdminState, context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.loginApi.adminRegister(
                    token,
                    AdminCreateRequestDto(
                        username = adminState.username,
                        firstName = adminState.firstName,
                        lastName = adminState.lastName,
                        birthDate = adminState.birthdate,
                        gender = adminState.gender,
                        pic = Converter.convertUriToBase64(context, adminState.profilePic)
                    )
                    //ConverterUri.convert(context, pic, "profilePic")!!
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