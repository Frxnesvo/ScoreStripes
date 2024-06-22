package com.example.clientadmin.authentication

import android.content.Context
import com.example.clientadmin.utils.TokenStoreUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken: String? = TokenStoreUtils.getToken()

        println("accessToken null? ---> $accessToken")

        if(accessToken != null){
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            println("Authorization header added to request")
        }

        val response = chain.proceed(request)

        if (TokenStoreUtils.getToken() != null &&  response.code == 401) {
            println("Access token scaduto")
            //TODO logout e navigazione
        }
        return response
    }
}