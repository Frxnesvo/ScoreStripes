package com.example.clientadmin.activity

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
import com.example.clientadmin.R
import com.example.clientadmin.ui.theme.ClientAdminTheme
import com.example.clientadmin.viewmodels.LoginViewModel
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()

            ClientAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
//                    Register(
//                        token = "token",
//                        navController = navHostController,
//                        loginViewModel = LoginViewModel(),
//                        loginFormViewModel = LoginFormViewModel()
//                    )
                    Navigation(navController = navHostController)
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
        val loginViewModel = LoginViewModel()
        //INDEX
        composable(route = "index"){
            IndexPage(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        //REGISTER
        composable(route = "register/{token}"){
            it.arguments?.getString("token").let {
                token ->
                if (token != null)
                    Register(
                        token = token,
                        navController = navController,
                        loginViewModel = loginViewModel,
                        loginFormViewModel = LoginFormViewModel()
                    )
            }
        }

        //SCAFFOLD
        composable(route = "scaffold/{token}"){
            it.arguments?.getString("token").let {
                token ->
                if (token != null) {
                    Scaffold(loginViewModel = loginViewModel, token = token)
                }
            }
        }
    }
}
