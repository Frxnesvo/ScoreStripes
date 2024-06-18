package com.example.clientuser.activity

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.clientuser.R

@SuppressLint("SetJavaScriptEnabled")
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
                                "stripe_success" -> { navController.navigate("payment_success") }
                                "stripe_cancel" -> { navController.navigate("payment_failure") }
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
fun PaymentSuccessScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment successful"
        )
        CustomButton(
            text = "go back to shopping",
            background = R.color.secondary
        ) { navHostController.navigate("home") }
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
        ) { navHostController.navigate("home") } //TODO da vedere
    }
}

/*@Composable
fun SplashScreen(navController: NavHostController, orderViewModel: OrderViewModel) {
    val validate by orderViewModel.validateTransactionResult.collectAsState()

    LaunchedEffect(validate == "") {
        if (validate != "") {
            navController.navigate("payment_success") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    var growing by remember { mutableStateOf(true) }
    val size by animateFloatAsState(
        targetValue = if (growing) 200f else 100f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "circle_size_animation"
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
}*/