package com.example.clientuser.authentication

import com.example.clientuser.viewmodel.LogoutViewModel


object LogoutManager {
    lateinit var instance: LogoutViewModel
        private set

    fun initialize(logoutViewModel: LogoutViewModel){
        instance = logoutViewModel
    }
}