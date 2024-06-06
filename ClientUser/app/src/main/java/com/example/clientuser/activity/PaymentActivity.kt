package com.example.clientuser.activity

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.clientuser.viewmodel.OrderViewModel
import com.example.clientuser.R
import kotlinx.coroutines.delay

@Composable
fun WebViewScreen(paymentUrl: String, navController: NavHostController, onFinish: () -> Unit) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        request?.url?.let { uri ->
                            when (uri.host) {
                                "stripe_success" -> {
                                    uri.getQueryParameter("session_id")?.let { sessionId ->
                                        navController.navigate("splash_screen/$sessionId")
                                    }
                                }
                                "stripe_cancel" -> {
                                    navController.navigate("payment_failure")
                                }
                                else -> { return false }
                            }
                            onFinish()
                            return true
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
            }
        },
        update = { webView ->
            if (paymentUrl.isNotEmpty()) {
                webView.loadUrl(paymentUrl)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun PaymentSuccessScreen(result: String, navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = result
        )
        CustomButton(text = "go back to shopping", background = R.color.secondary) { navHostController.navigate("home") }
    }
}

@Composable
fun PaymentFailureScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment failed"
        )
        CustomButton(
            text = "go back to shopping",
            background = R.color.secondary
        ) { navHostController.navigate("home") }
    }
}

@Composable
fun SplashScreen(navController: NavHostController, orderViewModel: OrderViewModel) {
    //TODO val validate by orderViewModel.validateTransactionResult.collectAsState()

    LaunchedEffect(true) {//todo validate == ""
        if (true) { //todo !validate != ""
            navController.navigate("payment_success") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    var growing by remember { mutableStateOf(true) }
    val size by animateFloatAsState(
        targetValue = if (growing) 200f else 100f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    LaunchedEffect(size) {
        delay(1000)
        growing = !growing
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(
                    color = colorResource(id = R.color.secondary),
                    shape = CircleShape
                )
        )
    }
}