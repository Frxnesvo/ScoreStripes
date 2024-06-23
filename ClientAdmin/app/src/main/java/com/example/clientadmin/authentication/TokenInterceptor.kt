package com.example.clientadmin.authentication

import com.example.clientadmin.utils.TokenStoreUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken: String? = TokenStoreUtils.getToken()


        if(accessToken != null){
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }

        val response = chain.proceed(request)

        if (TokenStoreUtils.getToken() != null &&  response.code == 403) {      //TODO sostituire con 401
            println("Access token scaduto")
            LogoutManager.instance.logout()
        }
        return response
    }
}