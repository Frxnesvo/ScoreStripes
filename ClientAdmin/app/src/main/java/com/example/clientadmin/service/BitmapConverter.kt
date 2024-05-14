package com.example.clientadmin.service

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

object BitmapConverter {
    fun bitmapToMultipartBodyPart(bitmap: Bitmap): MultipartBody.Part {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
        val data = bos.toByteArray()
        return MultipartBody.Part.createFormData("image", "image.jpg", RequestBody.create(MediaType.parse("image/*"), data))
    }
}
