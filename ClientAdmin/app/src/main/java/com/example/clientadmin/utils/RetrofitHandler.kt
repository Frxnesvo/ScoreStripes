package com.example.clientadmin.utils

import android.content.Context
import com.example.clientadmin.R
import com.example.clientadmin.api.ClubApiService
import com.example.clientadmin.api.CustomerApiService
import com.example.clientadmin.api.LeagueApiService
import com.example.clientadmin.api.LoginApiService
import com.example.clientadmin.api.LogoutApiService
import com.example.clientadmin.api.OrdersApiService
import com.example.clientadmin.api.ProductApiService
import com.example.clientadmin.api.ProductVariantApiService
import com.example.clientadmin.authentication.TokenInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object RetrofitHandler {
//    private val logging = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
////    private val client = OkHttpClient.Builder()
////        .addInterceptor(logging)
////        .addInterceptor(TokenInterceptor())
////        .build()
//
//    private val moshi = Moshi.Builder()
//        .add(LocalDateAdapter)
//        .build()
//
//    private val retrofit by lazy {
//        val client = getCustomTrustClient(AppContext.appContext)
//        Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080")
//            .client(client)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
    private fun createRetrofit(context: Context): Retrofit {
        val client = getCustomTrustClient(context)

        val moshi = Moshi.Builder()
            .add(LocalDateAdapter)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://192.168.1.55:8443") // Ensure you're using HTTPS
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    lateinit var leagueApi: LeagueApiService
    lateinit var clubApi: ClubApiService
    lateinit var customerApi: CustomerApiService
    lateinit var productApi: ProductApiService
    lateinit var ordersApi: OrdersApiService
    lateinit var productVariantApi: ProductVariantApiService
    lateinit var loginApi: LoginApiService
    lateinit var logoutApi: LogoutApiService

    fun initialize(context: Context) {
        val retrofit = createRetrofit(context)
        leagueApi = retrofit.create(LeagueApiService::class.java)
        clubApi = retrofit.create(ClubApiService::class.java)
        customerApi = retrofit.create(CustomerApiService::class.java)
        productApi = retrofit.create(ProductApiService::class.java)
        ordersApi = retrofit.create(OrdersApiService::class.java)
        productVariantApi = retrofit.create(ProductVariantApiService::class.java)
        loginApi = retrofit.create(LoginApiService::class.java)
        logoutApi = retrofit.create(LogoutApiService::class.java)
    }


    private fun getCustomTrustClient(context: Context): OkHttpClient {
        try {
            // Load CAs from an InputStream
            val cf = CertificateFactory.getInstance("X.509")
            val caInput = context.resources.openRawResource(R.raw.certificate) // Change `certificate` to the actual name of your .cer file
            val ca = cf.generateCertificate(caInput)
            caInput.close()

            // Create a KeyStore containing our trusted CAs
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            // Create a TrustManager that trusts the CAs in our KeyStore
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)

            // Create an SSLContext that uses our TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, null)

            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val hostnameVerifier = HostnameVerifier { _, _ -> true }

            return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
                .hostnameVerifier(hostnameVerifier)
                .addInterceptor(logging)
                .addInterceptor(TokenInterceptor())
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}