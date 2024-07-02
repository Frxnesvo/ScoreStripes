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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientuser.LocalLoginViewModel
import com.example.clientuser.R
import com.example.clientuser.authentication.GoogleAuth
import com.example.clientuser.authentication.LogoutManager
import com.example.clientuser.authentication.UserSession
import com.example.clientuser.ui.theme.ClientUserTheme
import com.example.clientuser.utils.RetrofitHandler
import com.example.clientuser.utils.ToastManager
import com.example.clientuser.utils.TokenStoreUtils
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.LogoutViewModel
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userSession = UserSession()
        RetrofitHandler.initialize(this.applicationContext)
        val loginViewModel = LoginViewModel(userSession)
        TokenStoreUtils.initialize(this.applicationContext)
        ToastManager.initialize(this.applicationContext)
        LogoutManager.initialize(LogoutViewModel(userSession))

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val token = GoogleAuth.manageLoginResult(result)
            if (token != null) loginViewModel.login(token)
        }

        super.onCreate(savedInstanceState)

        setContent {
            ClientUserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    CompositionLocalProvider(
                        LocalLoginViewModel provides loginViewModel
                    ) {
                        Navigation(
                            navController = rememberNavController(),
                            loginFormViewModel = LoginFormViewModel(),
                            signInLauncher = signInLauncher,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    loginFormViewModel: LoginFormViewModel,
    signInLauncher: ActivityResultLauncher<Intent>,
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
                        loginFormViewModel = loginFormViewModel,
                        addressFormViewModel = AddressFormViewModel(null)
                    )
            }
        }
    }
}