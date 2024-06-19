package com.example.clientadmin.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class TokenStoreUtils(private val context: Context) {

    companion object {
        private const val KEY_ALIAS = "JWTKeyAlias"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val PREFS_NAME = "MyAppPreferences"
        private const val PREFS_JWT = "ENCRYPTED_JWT"
        private const val PREFS_IV = "JWT_IV"
    }

    init {
        generateKey()
    }

    private fun generateKey() {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }

    fun storeToken(token: String) {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedToken = cipher.doFinal(token.toByteArray(Charsets.UTF_8))

        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(PREFS_JWT, Base64.encodeToString(encryptedToken, Base64.DEFAULT))
            putString(PREFS_IV, Base64.encodeToString(iv, Base64.DEFAULT))
            apply()
        }
    }

    fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encryptedTokenBase64 = sharedPreferences.getString(PREFS_JWT, null)
        val ivBase64 = sharedPreferences.getString(PREFS_IV, null)

        if (encryptedTokenBase64 != null && ivBase64 != null) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey

            val iv = Base64.decode(ivBase64, Base64.DEFAULT)
            val encryptedToken = Base64.decode(encryptedTokenBase64, Base64.DEFAULT)

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            val decryptedToken = cipher.doFinal(encryptedToken)
            return String(decryptedToken, Charsets.UTF_8)
        }
        return null
    }
}