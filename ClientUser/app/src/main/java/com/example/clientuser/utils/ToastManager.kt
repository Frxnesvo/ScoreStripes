package com.example.clientuser.utils

import android.content.Context
import android.widget.Toast

object ToastManager {
    private lateinit var toast: Toast
    fun initialize(context: Context){
        toast = Toast(context)
    }
    fun show(message: String) {
        toast.setText(message)
        toast.show()
    }
}