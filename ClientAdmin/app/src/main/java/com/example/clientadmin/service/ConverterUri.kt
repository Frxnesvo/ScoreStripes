package com.example.clientadmin.service

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

object ConverterUri {
    private fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
        var name = ""
        val returnCursor = contentResolver.query(uri, null, null, null, null)
        returnCursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }

    fun convert(context: Context, uri: Uri, fieldName: String): MultipartBody.Part? {
        val contentResolver: ContentResolver = context.contentResolver

        val fileName = getFileName(contentResolver, uri)
        val mimeType = contentResolver.getType(uri)

        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            val fileBytes = stream.readBytes()
            val requestBody =
                fileBytes.toRequestBody(mimeType?.toMediaTypeOrNull(), 0)
            return MultipartBody.Part.createFormData(fieldName, fileName, requestBody)
        }
        return null
    }
}