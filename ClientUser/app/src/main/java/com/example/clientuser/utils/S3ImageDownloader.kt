package com.example.clientuser.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object S3ImageDownloader {
    private var image: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    fun getImageForBucket(presignedUrl: String): Bitmap{
        download(presignedUrl)
        return image
    }

    private fun download(presignedUrl: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(presignedUrl)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Error getting image from bucket: $response")

                    response.body?.let { body ->
                        val bytes = body.bytes()
                        image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                }
            }
        } catch (e: Exception) {
            println("Exception getting image from bucket: ${e.message}")
        }
    }
}