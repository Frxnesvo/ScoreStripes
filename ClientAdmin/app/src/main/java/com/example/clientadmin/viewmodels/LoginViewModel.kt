package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LoginViewModel: ViewModel() {
    private val _user = MutableStateFlow<Admin?>(null)
    val user: StateFlow<Admin?> = _user

    private val _addError = mutableStateOf("")
    val addError = _addError

    fun getAdminFromToken(token: String){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.checkAdminLogin("Bearer $token").awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { _user.value = Admin.fromDto(it) }
                } else {
                    println("Error checking admin login: ${response.message()}")
                }
            }
        } catch (e: Exception) {
            println("Exception checking admin login: ${e.message}")
        }
    }

    fun register(token: String, adminCreateRequestDto: AdminCreateRequestDto, pic: Bitmap): Boolean{
        try {
            Admin(
                username = adminCreateRequestDto.username,
                firstName = null,
                lastName = null,
                email = null,
                birthDate = adminCreateRequestDto.birthDate,
                gender = adminCreateRequestDto.gender,
                pic = pic
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.adminRegister(
                    "Bearer $token",
                    adminCreateRequestDto.username,
                    adminCreateRequestDto.birthDate,
                    adminCreateRequestDto.gender,
                    ConverterBitmap.convert(bitmap = pic, fieldName = "profilePic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { _user.value = Admin.fromDto(it) }
                } else {
                    ("Error registering admin: ${response.message()}")
                }
            }
            return true
        }
        catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            return false
        }
    }
}