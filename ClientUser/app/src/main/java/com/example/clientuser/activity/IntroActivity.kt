package com.example.clientuser.activity

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientuser.LocalLoginViewModel
import com.example.clientuser.R
import com.example.clientuser.authentication.GoogleAuth

@Composable
fun IndexPage(
    navController: NavHostController,
    signInLauncher: ActivityResultLauncher<Intent>
) {
    val loginViewModel = LocalLoginViewModel.current
    val context = LocalContext.current

    val isLoggedIn = loginViewModel.isLoggedIn

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

                CustomButton(
                    background = R.color.secondary50,
                    text = stringResource(id = R.string.sign_in)
                ) {
                    val googleSignInClient = GoogleAuth.getClient(context)
                    googleSignInClient.signOut().addOnCompleteListener {
                        val signInIntent = googleSignInClient.signInIntent
                        signInLauncher.launch(signInIntent)
                    }
                }

                if(isLoggedIn.value){
                    LocalLoginViewModel.current.user.collectAsState().value?.let {
                        val intent = Intent(context, MainActivity2::class.java)
                        intent.putExtra("customer", it)
                        context.startActivity(intent)
                    }
                } else if(loginViewModel.goToRegister.value)
                    navController.navigate("register/${loginViewModel.token.value}")

                TextButton(
                    onClick = {
                        val intent = Intent(context, MainActivity2::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.enter_as_guest),
                        color = colorResource(id = R.color.white50),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 5.sp
                        )
                    )
                }
            }
        }
    }
}