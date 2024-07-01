package com.example.clientuser.utils

import android.content.Context
import com.example.clientuser.R
import com.example.clientuser.api.CustomerApiService
import com.example.clientuser.api.CartApiService
import com.example.clientuser.api.ClubApiService
import com.example.clientuser.api.LeagueApiService
import com.example.clientuser.api.LoginApiService
import com.example.clientuser.api.LogoutApiService
import com.example.clientuser.api.OrderApiService
import com.example.clientuser.api.ProductApiService
import com.example.clientuser.api.WishListApiService
import com.example.clientuser.authentication.TokenInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object RetrofitHandler {

    // Funzione per creare l'istanza di Retrofit con TLS
    private fun createRetrofit(context: Context): Retrofit {
        val client = getCustomTrustClient(context)

        val moshi = Moshi.Builder()
            .add(LocalDateAdapter)
            .add(AddressAdapter)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://192.168.1.9:8443") // Assicurati di usare HTTPS e il corretto indirizzo IP e porta
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Dichiarazioni delle API
    lateinit var loginApi: LoginApiService
    lateinit var leagueApi: LeagueApiService
    lateinit var cartApi: CartApiService
    lateinit var customerApi: CustomerApiService
    lateinit var clubApi: ClubApiService
    lateinit var orderApi: OrderApiService
    lateinit var productApi: ProductApiService
    lateinit var wishListApi: WishListApiService
    lateinit var logoutApi: LogoutApiService

    // Funzione per inizializzare RetrofitHandler
    fun initialize(context: Context) {
        val retrofit = createRetrofit(context)
        loginApi = retrofit.create(LoginApiService::class.java)
        leagueApi = retrofit.create(LeagueApiService::class.java)
        cartApi = retrofit.create(CartApiService::class.java)
        customerApi = retrofit.create(CustomerApiService::class.java)
        clubApi = retrofit.create(ClubApiService::class.java)
        orderApi = retrofit.create(OrderApiService::class.java)
        productApi = retrofit.create(ProductApiService::class.java)
        wishListApi = retrofit.create(WishListApiService::class.java)
        logoutApi = retrofit.create(LogoutApiService::class.java)
    }

    // Funzione per ottenere un client OkHttp con il certificato custom
    private fun getCustomTrustClient(context: Context): OkHttpClient {
        try {
            // Carica i CA da un InputStream
            val cf = CertificateFactory.getInstance("X.509")
            val caInput = context.resources.openRawResource(R.raw.certificate) // Assicurati che il nome del file corrisponda al tuo .cer
            val ca = cf.generateCertificate(caInput)
            caInput.close()

            // Crea un KeyStore contenente i CA di fiducia
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            // Crea un TrustManager che si fida dei CA nel nostro KeyStore
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)

            // Crea un SSLContext che usa il nostro TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, null)

            // Logging interceptor per debugging
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Disabilita la verifica del nome dell'host (solo per sviluppo/testing)
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