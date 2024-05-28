package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.authentication.GoogleAuth
import com.example.clientadmin.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun IndexPage(navController : NavHostController, loginViewModel: LoginViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.collection),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp)
        )
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.black50))
                .fillMaxSize()
                .padding(vertical = 50.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Title(colorStripes = colorResource(id = R.color.white))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current

                CustomButton(
                    background = R.color.secondary50,
                    text = stringResource(id = R.string.sign_in)
                ) {
                    val auth = GoogleAuth(context)
                    coroutineScope.launch {
                        val token = auth.getGoogleCredential()
                        if (token != null) {
                            loginViewModel.getAdminFromToken(token)
                            val admin = loginViewModel.user.value
                            if (admin != null) navController.navigate("scaffold")
                            else navController.navigate("register/${token}")
                        }
                    }
                }

                /*TextButton(onClick = { globalIndex.intValue = 3 }) {
                    Text(
                        text = "enter as guest",
                        color = colorResource(id = R.color.white50),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 5.sp
                        )
                    )
                }*/
            }
        }
    }
}