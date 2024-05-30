package com.example.clientuser.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientuser.R
import com.example.clientuser.ui.theme.ClientUserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClientUserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    Navigation(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        modifier = Modifier.background(colorResource(R.color.primary)),
        navController = navController,
        startDestination = "index"
    ){
        //INDEX
        composable(route = "index"){
            IndexPage(
                navController = navController,
                //loginViewModel = TODO("fare il view model")
            )
        }

        //REGISTER
        composable(route = "register/{token}"){
            it.arguments?.getString("token").let {
                token ->
                if (token != null)
                    Login(
                        token = token,
                        navController = navController,
                        //loginViewModel = TODO("fare il view model"),
                        //loginFormViewModel = TODO("fare il form view model")
                    )
            }
        }

        //SCAFFOLD
        composable(route = "scaffold/{token}"){
            it.arguments?.getString("token").let {
                token ->
                if (token != null) {
                    //Scaffold(loginViewModel = loginViewModel, token = token)
                }
            }
        }
    }
}