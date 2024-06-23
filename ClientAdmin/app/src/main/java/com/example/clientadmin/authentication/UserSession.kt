package com.example.clientadmin.authentication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.clientadmin.model.Admin
import kotlinx.coroutines.flow.MutableStateFlow

data class UserSession(
    val isLoggedIn: MutableState<Boolean> = mutableStateOf(false),
    val user: MutableStateFlow<Admin?> = MutableStateFlow<Admin?>(null)
)