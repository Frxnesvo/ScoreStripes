package com.example.clientadmin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class S3ImageDownloader {
    companion object {
        fun download(presignedUrl: String): Flow<Bitmap> = flow {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(presignedUrl)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Error getting image from bucket: $response")

                    response.body?.let { body ->
                        val image = body.bytes()
                        emit(BitmapFactory.decodeByteArray(image, 0, image.size))
                    }
                }
            }
            catch (e : Exception){
                println("Exception getting image from bucket: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }
}