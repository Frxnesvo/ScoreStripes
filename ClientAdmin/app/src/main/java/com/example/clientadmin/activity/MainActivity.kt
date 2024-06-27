package com.example.clientadmin.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientadmin.LocalClubViewModel
import com.example.clientadmin.LocalCustomersViewModel
import com.example.clientadmin.LocalHomeViewModel
import com.example.clientadmin.LocalLeagueViewModel
import com.example.clientadmin.LocalProductsViewModel
import com.example.clientadmin.R
import com.example.clientadmin.authentication.GoogleAuth
import com.example.clientadmin.authentication.LogoutManager
import com.example.clientadmin.authentication.UserSession
import com.example.clientadmin.ui.theme.ClientAdminTheme
import com.example.clientadmin.utils.TokenStoreUtils
import com.example.clientadmin.viewmodels.LoginViewModel
import com.example.clientadmin.viewmodels.LogoutViewModel
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel
import com.example.clientadmin.utils.ToastManager
import com.example.clientadmin.viewmodels.ClubViewModel
import com.example.clientadmin.viewmodels.CustomersViewModel
import com.example.clientadmin.viewmodels.HomeViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userSession = UserSession()

        ToastManager.initialize(this.applicationContext)
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

        window.statusBarColor = R.color.primary

        setContent {
            val navHostController = rememberNavController()

            ClientAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    CompositionLocalProvider(
                        LocalCustomersViewModel provides CustomersViewModel(),
                        LocalProductsViewModel provides ProductsViewModel(),
                        LocalClubViewModel provides ClubViewModel(),
                        LocalLeagueViewModel provides LeagueViewModel(),
                        LocalHomeViewModel provides HomeViewModel()
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(value = "", onValueChange = {}, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(0.05f))
            TextField(value = "", onValueChange = {}, modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun Navigation(
    signInLauncher: ActivityResultLauncher<Intent>,
    navController: NavHostController,
    loginViewModel: LoginViewModel
){
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



