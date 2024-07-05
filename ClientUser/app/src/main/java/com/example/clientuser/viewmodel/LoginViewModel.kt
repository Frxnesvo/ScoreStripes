package com.example.clientuser.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.authentication.UserSession
import com.example.clientuser.model.Address
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.utils.ConverterBitmap
import com.example.clientuser.utils.RetrofitHandler
import com.example.clientuser.utils.ToastManager
import com.example.clientuser.utils.TokenStoreUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.awaitResponse
import java.time.LocalDate


class LoginViewModel(userSession: UserSession): ViewModel() {
    private val _goToRegister = mutableStateOf(false)
    val goToRegister: State<Boolean> = _goToRegister

    private val _user = userSession.user
    val user = _user.asStateFlow()

    private val _isLoggedIn = userSession.isLoggedIn
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _token = mutableStateOf("")
    val token = _token

    fun login(idToken: String){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.login(mapOf(Pair("idToken", idToken))).awaitResponse()
                if(response.isSuccessful){

                    response.body()?.let { authResponseDto ->
                        _user.value = authResponseDto.copy(
                            jwt = ""
                        )
                        _isLoggedIn.value = true
                        _token.value = ""

                        withContext(Dispatchers.Main) {
                            TokenStoreUtils.storeToken(authResponseDto.jwt)
                        }
                        ToastManager.show("Login successful")
                    }
                }
                else{
                    if(response.code() == 409){
                        _isLoggedIn.value = false
                        _goToRegister.value = true
                        _token.value = idToken
                    }
                    else if(response.code() == 500) {
                        ToastManager.show("This account is registered as admin")
                    } else println("Error customer login: ${response.message()}")
                }
            }
        }catch (e: Exception){
            _isLoggedIn.value = false
            println("Exception customer login: ${e.message}")
        }
    }


    fun register(token: String, username: String, birthDate: LocalDate, gender: Gender, address: Address, favouriteTeam: String, pic: Bitmap){
        try{
            CoroutineScope(Dispatchers.IO).launch {

                val response = RetrofitHandler.loginApi.customerRegister(
                    idToken = token.toRequestBody("text/plain".toMediaTypeOrNull()),
                    username = username.toRequestBody("text/plain".toMediaTypeOrNull()),
                    birthDate = birthDate.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    gender = gender.name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    favoriteTeam = favouriteTeam.toRequestBody("text/plain".toMediaTypeOrNull()),
                    imageProfile = ConverterBitmap.convert(pic, "profilePic"),

                    street = address.street.toRequestBody("text/plain".toMediaTypeOrNull()),
                    city = address.city.toRequestBody("text/plain".toMediaTypeOrNull()),
                    state = address.state.toRequestBody("text/plain".toMediaTypeOrNull()),
                    zipCode = address.zipCode.toRequestBody("text/plain".toMediaTypeOrNull()),
                    civicNumber = address.civicNumber.toRequestBody("text/plain".toMediaTypeOrNull()),
                    defaultAddress = address.defaultAddress.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                ).awaitResponse()

                if(response.isSuccessful) {
                    _token.value = ""
                    ToastManager.show("Register successful")
                }
                else println("Error customer register: ${response.message()}")
                goToLogin()

            }
        }catch (e: Exception){
            goToLogin()
            _token.value = ""
        }
    }

    fun goToLogin(){
        _isLoggedIn.value = false
        _goToRegister.value = false
    }
}