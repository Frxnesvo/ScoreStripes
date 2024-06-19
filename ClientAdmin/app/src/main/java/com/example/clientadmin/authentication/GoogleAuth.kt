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
        //prendo l'id client dal file gradle.properties
        private val WEB_SERVER_CLIENT_ID= "774497332630-engaqhas88dv6rpmrpjfnad09i6jloef.apps.googleusercontent.com"

        fun getClient(context: Context): GoogleSignInClient{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_SERVER_CLIENT_ID)  // Usa l'ID client Android
                .requestEmail()
                .build()
            return GoogleSignIn.getClient(context, gso)
        }

        fun signOut(context: Context){
            getClient(context).signOut()
        }



        fun manageLoginResult(result: ActivityResult): String?{
            try {
                println(result.resultCode)
                val data: Intent? = result.data

                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    println("ID TOKEN in manage: ${account?.idToken}")
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