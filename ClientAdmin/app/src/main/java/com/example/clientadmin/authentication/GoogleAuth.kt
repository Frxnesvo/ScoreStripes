package com.example.clientadmin.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GoogleAuth{
    companion object {

        private val WEB_SERVER_CLIENT_ID = "774497332630-engaqhas88dv6rpmrpjfnad09i6jloef.apps.googleusercontent.com" //TODO non va messo qui

        fun getClient(context: Context): GoogleSignInClient{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_SERVER_CLIENT_ID)  // Usa l'ID client Android
                .requestEmail()
                .build()

            return GoogleSignIn.getClient(context, gso)
        }

        fun manageLoginResult(result: ActivityResult): String?{
            try {
                val data: Intent? = result.data

                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    return task.result.idToken
                }
            }
            catch (e: ApiException) {
                e.printStackTrace()
            }
            return null
        }
    }
}