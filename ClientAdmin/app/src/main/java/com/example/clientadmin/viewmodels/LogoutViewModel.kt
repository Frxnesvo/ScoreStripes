package com.example.clientadmin.viewmodels

import androidx.compose.runtime.State
import com.example.clientadmin.authentication.UserSession
import com.example.clientadmin.model.Admin
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.utils.ToastManager
import com.example.clientadmin.utils.TokenStoreUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LogoutViewModel(userSession: UserSession){

    private val _user = userSession.user
    val user = _user.asStateFlow()

    private val _isLoggedIn = userSession.isLoggedIn
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    fun logout(){
        try {
            CoroutineScope(Dispatchers.Default).launch {
                val response = RetrofitHandler.logoutApi.logout().awaitResponse()
                if(response.isSuccessful){
                    TokenStoreUtils.clearToken()
                    _logoutEvent.emit(Unit)
                    _user.value = null
                    _isLoggedIn.value = false
                    ToastManager.show("Logout success")
                }
                else {
                    println("Error logout: ${response.message()}")
                    ToastManager.show("Error logout")
                }
            }

        }catch (e: Exception){
            println("Exception logout: ${e.message}")
        }
    }
}