package com.example.clientuser.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.clientuser.authentication.GoogleAuth
import com.example.clientuser.model.Customer
import com.example.clientuser.ui.theme.ClientUserTheme
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel = LoginViewModel()

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val token = GoogleAuth.manageLoginResult(result)
            if (token != null)
                loginViewModel.login(token, this.applicationContext)
        }
        setContent {
            ClientUserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    Navigation(
                        navController = rememberNavController(),
                        loginViewModel = loginViewModel,
                        loginFormViewModel = LoginFormViewModel(),
                        signInLauncher = signInLauncher
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    loginFormViewModel: LoginFormViewModel,
    signInLauncher: ActivityResultLauncher<Intent>
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
                loginViewModel = loginViewModel,
                signInLauncher = signInLauncher
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
                }

                Scaffold(customer!!)
            }
        }
    }
}