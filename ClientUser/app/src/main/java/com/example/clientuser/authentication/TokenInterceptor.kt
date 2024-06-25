package com.example.clientuser.authentication

import com.example.clientuser.utils.TokenStoreUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken: String? = TokenStoreUtils.getToken()


        if(accessToken != null){
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .header("Cache-Control", "no-cache")
                .header("Cache-Control", "no-store")
                .header("Pragma", "no-cache")
                .build()
        }

        val response = chain.proceed(request)

        if (TokenStoreUtils.getToken() != null &&  response.code == 401) {
            println("Access token scaduto")
            LogoutManager.instance.logout()
        }
        return response
    }
}