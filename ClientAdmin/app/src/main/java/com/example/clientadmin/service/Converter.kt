package com.example.clientadmin.service

import android.content.Context
import android.net.Uri
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

object Converter {
    suspend fun convertUriToBase64(context: Context, uri: Uri): String {
        return withContext(Dispatchers.IO) {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            bytes?.let {
                Base64.encodeToString(it, Base64.DEFAULT)
            } ?: ""
        }
    }
}