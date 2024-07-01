package com.example.clientadmin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object S3ImageDownloader {
    suspend fun getImageFromBucket(principal: String): Bitmap {
        var bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val job = CoroutineScope(Dispatchers.IO).launch {
            download(principal).collect { downloadedBitmap ->
                bitmap = downloadedBitmap
            }
        }
        job.join()
        return bitmap
    }

    private fun download(presignedUrl: String): Flow<Bitmap> = flow{
        try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(presignedUrl)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Error getting image from bucket: $response")

                    response.body?.let { body ->
                        val bytes = body.bytes()
                        emit( BitmapFactory.decodeByteArray(bytes, 0, bytes.size) )
                    }
                }
        } catch (e: Exception) {
            println("Exception getting image from bucket: ${e.message}")
        }
    }
}