package com.example.clientuser.authentication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.clientuser.model.Customer
import kotlinx.coroutines.flow.MutableStateFlow

data class UserSession(
    val isLoggedIn: MutableState<Boolean> = mutableStateOf(false),
    val user: MutableStateFlow<Customer?> = MutableStateFlow<Customer?>(null)
)