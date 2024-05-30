package com.example.clientadmin.authentication

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.clientadmin.model.dto.GoogleUserDto
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException


class GoogleAuth(private val context: Context){
    private val credentialManager = CredentialManager.create(context)
    private val WEB_SERVER_CLIENT_ID = "774497332630-engaqhas88dv6rpmrpjfnad09i6jloef.apps.googleusercontent.com"

    private fun buildGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // Query all google accounts on the device
            .setServerClientId(WEB_SERVER_CLIENT_ID)
            .build()
    }

    private fun buildRequest(googleIdOption: GetGoogleIdOption): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    suspend fun getGoogleCredential() : String?{
        val googleIdOption = buildGoogleIdOption()
        val request = buildRequest(googleIdOption)

        try {
            val credential = credentialManager.getCredential(context, request).credential
            if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                try {
                    return GoogleIdTokenCredential.createFrom(credential.data).idToken
                }catch (e: GoogleIdTokenParsingException) {
                    Log.e("INVALID GOOGLE ID TOKEN", "Received an invalid google id token response", e)
                }
            }

        } catch (e: GetCredentialException) {
            Log.e("Authentication", "GetCredentialException", e)
        }
        return null
    }

//    private fun handleSignIn(result: GetCredentialResponse) : GoogleUserDto? {
//        //Implementazione per gestire il risultato del login
//        val credential = result.credential
//
//        if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
//            try {
//                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//                val idToken = googleIdTokenCredential.idToken


//                val firstName = googleIdTokenCredential.givenName
//                val lastName = googleIdTokenCredential.familyName
//                val email = googleIdTokenCredential.id
//
//                return GoogleUserDto(firstName, lastName, email)
//            }catch (e: GoogleIdTokenParsingException) {
//                Log.e("INVALID GOOGLE ID TOKEN", "Received an invalid google id token response", e)
//            }
//        }
//
//        return null
//    }
}