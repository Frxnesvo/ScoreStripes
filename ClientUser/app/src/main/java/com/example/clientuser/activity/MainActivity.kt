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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientuser.R
import com.example.clientuser.model.Customer
import com.example.clientuser.ui.theme.ClientUserTheme
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel
import com.google.gson.Gson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClientUserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    Navigation(
                        navController = rememberNavController(),
                        loginViewModel = LoginViewModel(),
                        loginFormViewModel = LoginFormViewModel()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    loginViewModel : LoginViewModel,
    loginFormViewModel: LoginFormViewModel
){
    NavHost(
        modifier = Modifier.background(colorResource(R.color.primary)),
        navController = navController,
        startDestination = "index"
    ){
        //INDEX
        composable(
            route = "index"
        ){
            IndexPage(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        //REGISTER
        composable(
            route = "register/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ){
            it.arguments?.getString("token").let {
                token ->
                if (token != null)
                    Login(
                        token = token,
                        navController = navController,
                        loginViewModel = loginViewModel,
                        loginFormViewModel = loginFormViewModel
                    )
            }
        }

        //SCAFFOLD
        composable(
            route = "scaffold/{customer}",
            arguments = listOf(navArgument("customer") { type = NavType.StringType })
        ){
            it.arguments?.getString("customer").let { customerJson ->

                //TODO vedere come fare per la conversione dell'immagine
                val customer = customerJson?.let {
                    Gson().fromJson(it, Customer::class.java)
                    Json.decodeFromString<Customer>(it)
                }

                Scaffold(customer!!)
            }
        }
    }
}