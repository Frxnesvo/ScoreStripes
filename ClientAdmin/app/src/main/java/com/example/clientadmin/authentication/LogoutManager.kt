package com.example.clientadmin.authentication

import com.example.clientadmin.viewmodels.LogoutViewModel

object LogoutManager {
    lateinit var instance: LogoutViewModel
        private set

    fun initialize(logoutViewModel: LogoutViewModel){
        instance = logoutViewModel
    }
}