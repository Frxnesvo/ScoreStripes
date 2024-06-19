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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientadmin.R
import com.example.clientadmin.authentication.GoogleAuth
import com.example.clientadmin.ui.theme.ClientAdminTheme
import com.example.clientadmin.viewmodels.LoginViewModel
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val loginViewModel = LoginViewModel()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("774497332630-engaqhas88dv6rpmrpjfnad09i6jloef.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val token = GoogleAuth.manageLoginResult(result)
            if (token != null)
                loginViewModel.login(token, this.applicationContext)
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
            Scaffold(loginViewModel = loginViewModel)
        }
    }
}

