package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.enumerator.TokenState
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse
import java.time.format.DateTimeFormatter


class LoginViewModel: ViewModel() {
    private val _user = MutableStateFlow<Admin?>(null)
    val user: StateFlow<Admin?> = _user

    private val _addError = mutableStateOf("")
    val addError = _addError

    private val _isLoggedIn = mutableStateOf(TokenState.INVALID)
    val isLoggedIn = _isLoggedIn

    private val _token = mutableStateOf("")
    val token = _token


    fun login(idToken: String?){
        if(idToken != null) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = RetrofitHandler.loginApi.login(
                        mapOf(pair = Pair("idToken", idToken))
                    ).awaitResponse()
                    if (response.isSuccessful) {
                        //TODO salvare il token
                        _isLoggedIn.value = TokenState.LOGGED
                    } else {
                        if(response.code() == 409) {
                            _isLoggedIn.value = TokenState.REGISTER
                            _token.value = idToken
                        }
                        else println("Error login: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                println("Exception login: ${e.message}")
            }
        }
        else{
            _isLoggedIn.value = TokenState.INVALID
            println("invalid id token")
        }
    }

    fun goToLogin(){
        _isLoggedIn.value = TokenState.INVALID
    }

    fun register(token: String, adminCreateRequestDto: AdminCreateRequestDto, pic: Bitmap): Boolean{
        try {
            var returnValue = false
            Admin(
                username = adminCreateRequestDto.username,
                birthDate = adminCreateRequestDto.birthDate,
                gender = adminCreateRequestDto.gender,
                pic = pic
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.adminRegister(
                    MultipartBody.Part.createFormData("idToken", token),
                    MultipartBody.Part.createFormData("username", adminCreateRequestDto.username),
                    MultipartBody.Part.createFormData("birthDate", adminCreateRequestDto.birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE)),
                    MultipartBody.Part.createFormData("gender", adminCreateRequestDto.gender.name),
                    ConverterBitmap.convert(bitmap = pic, fieldName = "profilePic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    response.body()?.let { _user.value = Admin.fromDto(it) }
                    _isLoggedIn.value = TokenState.LOGGED
                    returnValue = true
                } else {
                    println("Error registering admin: ${response.message()}")
                    returnValue = false
                    _isLoggedIn.value = TokenState.INVALID
                }
            }
            return returnValue
        }
        catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            _isLoggedIn.value = TokenState.INVALID
            return false
        }
    }
}