package com.example.clientuser.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.authentication.UserSession
import com.example.clientuser.model.Address
import com.example.clientuser.model.Customer
import com.example.clientuser.model.dto.CustomerDto
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.utils.ConverterBitmap
import com.example.clientuser.utils.RetrofitHandler
import com.google.gson.Gson
import com.squareup.moshi.JsonWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.awaitResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class LoginState { LOGGED, REGISTER, NULL}

class LoginViewModel(userSession: UserSession): ViewModel() {

    private val _addError = mutableStateOf("")
    val addError = _addError                            //TODO da usare

    private val _goToRegister = mutableStateOf(false)
    val goToRegister: State<Boolean> = _goToRegister

    private val _user = userSession.user
    val user = _user.asStateFlow()

    private val _isLoggedIn = userSession.isLoggedIn
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _token = mutableStateOf("")
    val token = _token

    fun login(idToken: String, context: Context){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.login(mapOf(Pair("idToken", idToken))).awaitResponse()
                if(response.isSuccessful){
                    //TODO salvare il token
                    //TODO verificare che l'utente loggato sia un admin

                    response.body()?.let { customerDto ->
                        _user.value = Customer.fromDto(customerDto)
                        _isLoggedIn.value = true
                        _token.value = ""
                    }
                }
                else{
                    if(response.code() == 409){
                        _isLoggedIn.value = false
                        _goToRegister.value = true
                        _token.value = idToken
                    }
                    else println("Error customer login: ${response.message()}")
                }
            }
        }catch (e: Exception){
            _isLoggedIn.value = false
            println("Exception customer login: ${e.message}")
        }
    }

    fun register(token: String, username: String, birthDate: LocalDate, gender: Gender, address: Address, favouriteTeam: String, pic: Bitmap): Boolean{
        try{
            println("TOKEN GOOGLE register: $token")
            var returnValue = false
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

                if(response.isSuccessful){
                    _token.value = ""
                    returnValue = true
                }
                else{
                    println("Error customer register: ${response.message()}")
                    returnValue = false
                }
                goToLogin()

            }
            return returnValue
        }catch (e: Exception){
            _addError.value = e.message ?: "Unknown error"
            goToLogin()
            _token.value = ""
            return false
        }
    }

    fun goToLogin(){
        _isLoggedIn.value = false
        _goToRegister.value = false
    }

    fun updateCustomer(customerDto: CustomerDto){
        TODO("manca controller per l'update")
    }
}