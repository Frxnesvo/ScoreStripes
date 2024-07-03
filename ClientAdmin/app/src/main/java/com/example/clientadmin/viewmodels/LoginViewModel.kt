package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.authentication.UserSession
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.utils.ToastManager
import com.example.clientadmin.utils.TokenStoreUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.awaitResponse
import java.time.format.DateTimeFormatter

class LoginViewModel(userSession: UserSession): ViewModel() {
    private val _user = userSession.user
    val user = _user.asStateFlow()

    private val _addError = mutableStateOf("")
    val addError = _addError

    private val _isLoggedIn = userSession.isLoggedIn
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _goToRegister = mutableStateOf(false)
    val goToRegister: State<Boolean> = _goToRegister

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
                        response.body()?.let { authResponseDto ->
                            _user.value = authResponseDto
                            _isLoggedIn.value = true
                            _token.value = ""

                            withContext(Dispatchers.Main) {
                                TokenStoreUtils.storeToken(authResponseDto.jwt)
                            }
                        }
                        ToastManager.show("Login successful")

                    } else {
                        if(response.code() == 409) {
                            _isLoggedIn.value = false
                            _goToRegister.value = true
                            _token.value = idToken
                        }
                        else if(response.code() == 500) ToastManager.show("This account is registered as customer")
                        else println("Error login: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                println("Exception login: ${e.message}")
            }
        }
        else{
            _isLoggedIn.value = false
            println("invalid id token")
        }
    }

    fun goToLogin(){
        _isLoggedIn.value = false
        _goToRegister.value = false
    }

    fun register(token: String, adminCreateRequestDto: AdminCreateRequestDto, pic: Bitmap){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.adminRegister(
                    MultipartBody.Part.createFormData("idToken", token),
                    MultipartBody.Part.createFormData("username", adminCreateRequestDto.username),
                    MultipartBody.Part.createFormData("birthDate", adminCreateRequestDto.birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE)),
                    MultipartBody.Part.createFormData("gender", adminCreateRequestDto.gender.name),
                    ConverterBitmap.convert(bitmap = pic, fieldName = "profilePic")
                ).awaitResponse()

                if (response.isSuccessful) {
                    _token.value = ""
                    val responseMap = response.body()
                    println("Response server: ${responseMap?.get("message")}")
                }
                else println("Error registering admin: ${response.message()}")
                goToLogin()
            }
        }
        catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            goToLogin()
            _token.value = ""
        }
    }
}