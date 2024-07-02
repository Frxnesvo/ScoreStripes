package com.example.clientuser.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

object ConverterBitmap {
    fun convert(bitmap: Bitmap, fieldName: String): MultipartBody.Part {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)

        val requestBody = bos.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull(), 0)

        return MultipartBody.Part.createFormData(fieldName, "$fieldName.jpeg", requestBody)
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}