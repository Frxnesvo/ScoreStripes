package com.example.clientadmin.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientadmin.R
import com.example.clientadmin.ui.theme.ClientAdminTheme



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
                    Navigation(navController = navHostController)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewApp(){
    Navigation(navController = rememberNavController())
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
            IndexPage(navController = navController)
        }

        //REGISTER
        composable(route = "register/{firstname}/{lastname}/{email}"){backStackEntry ->
            val firstname = backStackEntry.arguments?.getString("firstname") ?: ""
            val lastname = backStackEntry.arguments?.getString("lastname") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""

            Login(navController = navController, firstname, lastname, email)
        }

        //SCAFFOLD
        composable(route = "scaffold"){
            Scaffold()
        }
    }
}
