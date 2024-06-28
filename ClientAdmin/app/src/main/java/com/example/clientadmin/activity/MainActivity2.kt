package com.example.clientadmin.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.clientadmin.LocalClubViewModel
import com.example.clientadmin.LocalCustomersViewModel
import com.example.clientadmin.LocalHomeViewModel
import com.example.clientadmin.LocalLeagueViewModel
import com.example.clientadmin.LocalProductsViewModel
import com.example.clientadmin.R
import com.example.clientadmin.model.Admin
import com.example.clientadmin.ui.theme.ClientAdminTheme
import com.example.clientadmin.viewmodels.ClubViewModel
import com.example.clientadmin.viewmodels.CustomersViewModel
import com.example.clientadmin.viewmodels.HomeViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductsViewModel

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val admin = intent.getSerializableExtra("admin") as Admin

        setContent{
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
                        Scaffold(admin = admin)
                    }
                }
            }
        }
    }
}