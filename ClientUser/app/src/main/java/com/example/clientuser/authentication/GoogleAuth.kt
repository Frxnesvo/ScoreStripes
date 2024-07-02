package com.example.clientuser.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.example.clientuser.BuildConfig

class GoogleAuth{
    companion object {

        private const val WEB_SERVER_CLIENT_ID = BuildConfig.WEB_SERVER_CLIENT_ID

        fun getClient(context: Context): GoogleSignInClient{
            println("IL CLIENT ID E' $WEB_SERVER_CLIENT_ID")
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_SERVER_CLIENT_ID)
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
                    return account.idToken
                }
            }
            catch (e: ApiException) {
                e.printStackTrace()
            }
            return null
        }
    }
}