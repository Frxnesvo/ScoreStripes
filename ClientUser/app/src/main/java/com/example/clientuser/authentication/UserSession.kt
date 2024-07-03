package com.example.clientuser.authentication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.clientuser.model.dto.AuthResponseDto
import kotlinx.coroutines.flow.MutableStateFlow

data class UserSession(
    val isLoggedIn: MutableState<Boolean> = mutableStateOf(false),
    val user: MutableStateFlow<AuthResponseDto?> = MutableStateFlow(null)
)