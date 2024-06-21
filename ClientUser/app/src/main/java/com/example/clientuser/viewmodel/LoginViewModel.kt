package com.example.clientuser.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.Address
import com.example.clientuser.model.Customer
import com.example.clientuser.model.dto.CustomerDto
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.utils.ConverterBitmap
import com.example.clientuser.utils.RetrofitHandler
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class LoginState { LOGGED, REGISTER, NULL}

class LoginViewModel: ViewModel() {
    //todo mettere il customerDto
    private val _user = MutableStateFlow<Customer?>(null)
    val user = _user

    private val _isLoggedIn = mutableStateOf(LoginState.NULL)
    val isLoggedIn = _isLoggedIn

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
                        _isLoggedIn.value = LoginState.LOGGED
                    }
                }
                else{
                    if(response.code() == 409){
                        _isLoggedIn.value = LoginState.REGISTER
                        _token.value = idToken
                    }
                    else println("Error customer login: ${response.message()}")
                }
            }
        }catch (e: Exception){
            println("Exception customer login: ${e.message}")
        }
    }

    fun register(token: String, username: String, birthDate: LocalDate, gender: Gender, address: Address, favouriteTeam: String, pic: Bitmap): Boolean{
        try{
            var returnValue = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.loginApi.customerRegister(
                    idToken = MultipartBody.Part.createFormData("idToken", token),
                    username = MultipartBody.Part.createFormData("username", username),
                    birthDate = MultipartBody.Part.createFormData("birthDate", birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE)),
                    gender = MultipartBody.Part.createFormData("gender", gender.name),
                    address = MultipartBody.Part.createFormData("address", Gson().toJson(address)),
                    favouriteTeam = MultipartBody.Part.createFormData("favouriteTeam", favouriteTeam),
                    imageProfile = ConverterBitmap.convert(bitmap = pic, fieldName = "profilePic")
                ).awaitResponse()

                if(response.isSuccessful) returnValue = true
                else{
                    println("Error customer register: ${response.message()}")
                    returnValue = false
                }
                _isLoggedIn.value = LoginState.NULL

            }
            return returnValue
        }catch (e: Exception){
            println("Exception customer register: ${e.message}")
            return false
        }
    }

    fun goToLogin(){
        _isLoggedIn.value = LoginState.NULL
    }

    fun updateCustomer(customerDto: CustomerDto){
        TODO("manca controller per l'update")
    }
}