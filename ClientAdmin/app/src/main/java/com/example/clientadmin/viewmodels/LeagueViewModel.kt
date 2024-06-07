package com.example.clientadmin.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.dto.LeagueCreateRequestDto
import com.example.clientadmin.service.ConverterUri
import com.example.clientadmin.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.awaitResponse
import java.io.ByteArrayOutputStream

class LeagueViewModel: ViewModel() {
    private val _leaguesNames = MutableStateFlow<List<String>>(emptyList())
    val leaguesNames = _leaguesNames

    init{
        fetchLeaguesNames()
    }

    private fun fetchLeaguesNames(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.leagueApi.getLeagueNames().awaitResponse()
                if (response.isSuccessful)
                    response.body()?.let { _leaguesNames.value = it }
                else
                    handleApiError(response.message())
            } catch (e: Exception) {
                handleApiError(e.message ?: "Unknown error")
            }
        }
    }

    fun addLeague(context: Context, name: String, pic: Bitmap){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.leagueApi.createLeague(
                    name = LeagueCreateRequestDto(name).name,
                    pic = createMultipartBodyPart(pic)
                ).awaitResponse()
                if (response.isSuccessful)
                    response.body()?.let { club -> _leaguesNames.value += club.name }
                else handleApiError(response.message())
            }
        } catch (e: Exception) {
            handleApiError(e.message ?: "Unknown error")
        }
    }

    private fun handleApiError(response: String) {
        println("Error in API call: $response")
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun byteArrayToRequestBody(byteArray: ByteArray, contentType: String = "image/jpeg"): RequestBody {
        return RequestBody.create(contentType.toMediaTypeOrNull(), byteArray)
    }

    fun createMultipartBodyPart(bitmap: Bitmap, partName: String = "pic", fileName: String = "image.jpg"): MultipartBody.Part {
        val byteArray = bitmapToByteArray(bitmap)
        val requestBody = byteArrayToRequestBody(byteArray)
        return MultipartBody.Part.createFormData(partName, fileName, requestBody)
    }
}