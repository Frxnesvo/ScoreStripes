package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.utils.TokenStoreUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.awaitResponse
import java.time.format.DateTimeFormatter

enum class LoginState { LOGGED, REGISTER, NULL }
class LoginViewModel: ViewModel() {
    private val _user = MutableStateFlow<Admin?>(null)
    val user: StateFlow<Admin?> = _user

    private val _addError = mutableStateOf("")
    val addError = _addError

    private val _isLoggedIn = mutableStateOf(LoginState.NULL)
    val isLoggedIn = _isLoggedIn

    private val _token = mutableStateOf("")
    val token = _token


    fun login(idToken: String? /* , context: Context */ ){
        println("SONO NEL LOGIN VIEW MODEL")
        println("ID TOKEN: $idToken")
        if(idToken != null) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = RetrofitHandler.loginApi.login(
                        mapOf(pair = Pair("idToken", idToken))
                    ).awaitResponse()
                    if (response.isSuccessful) {
                        println("Response server: ${response.body()}")
                        //TODO verificare che l'utente loggato sia un admin

                        response.body()?.let { authResponseDto ->
                            _user.value = Admin.fromDto(authResponseDto)
                            _isLoggedIn.value = LoginState.LOGGED
                            _token.value = ""

                            withContext(Dispatchers.Main) {
                                TokenStoreUtils.storeToken(authResponseDto.jwt)
//                                val tokenStoreUtils = TokenStoreUtils(context)
//                                tokenStoreUtils.storeToken(jwt)
                            }
                        }

                    } else {
                        if(response.code() == 409) {
                            _isLoggedIn.value = LoginState.REGISTER
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
            _isLoggedIn.value = LoginState.NULL
            println("invalid id token")
        }
    }

    fun goToLogin(){
        _isLoggedIn.value = LoginState.NULL
    }

    fun register(token: String, adminCreateRequestDto: AdminCreateRequestDto, pic: Bitmap): Boolean{
        try {
            require(Admin.validateProfilePic(pic)) { "Invalid profile picture" }
            require(Admin.validateUsername(adminCreateRequestDto.username)) { "Invalid username: must be between 3 and 20 characters. At least 1 upper case character" }
            require(Admin.validateBirthdate(adminCreateRequestDto.birthDate)) { "Invalid birthdate: must be before the current date" }
            var returnValue = false
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
                    returnValue = true
                }
                else {
                    println("Error registering admin: ${response.message()}")
                    returnValue = false
                }
                _isLoggedIn.value = LoginState.NULL
            }
            return returnValue
        }
        catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            _isLoggedIn.value = LoginState.NULL
            _token.value = ""
            return false
        }
    }
}