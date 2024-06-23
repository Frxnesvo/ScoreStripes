package com.example.clientadmin.activity

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientadmin.R
import com.example.clientadmin.authentication.GoogleAuth
import com.example.clientadmin.authentication.LogoutManager
import com.example.clientadmin.authentication.UserSession
import com.example.clientadmin.ui.theme.ClientAdminTheme
import com.example.clientadmin.utils.TokenStoreUtils
import com.example.clientadmin.viewmodels.LoginViewModel
import com.example.clientadmin.viewmodels.LogoutViewModel
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel
import androidx.compose.runtime.LaunchedEffect
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userSession = UserSession()

        TokenStoreUtils.initialize(this.applicationContext)

        val loginViewModel = LoginViewModel(userSession)
        LogoutManager.initialize(LogoutViewModel(userSession))

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val token = GoogleAuth.manageLoginResult(result)
            if (token != null)
                loginViewModel.login(token /* , this.applicationContext */ )
            else println("Token is null")
            GoogleAuth.signOut(this)
        }

        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()

            ClientAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    Navigation(
                        signInLauncher = signInLauncher,
                        navController = navHostController,
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }


}

@Composable
fun Navigation(signInLauncher: ActivityResultLauncher<Intent>, navController: NavHostController, loginViewModel: LoginViewModel){
    NavHost(
        modifier = Modifier.background(colorResource(R.color.primary)),
        navController = navController,
        startDestination = "index"
    ){
        //INDEX
        composable(route = "index"){
            IndexPage(
                signInLauncher = signInLauncher,
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
        composable(route = "scaffold"){
            Scaffold(
                loginViewModel = loginViewModel,
                navHostController = navController
            )
        }
    }
}



