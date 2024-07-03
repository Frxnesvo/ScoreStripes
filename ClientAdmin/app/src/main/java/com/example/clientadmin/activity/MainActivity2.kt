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
import com.example.clientadmin.model.dto.AuthResponseDto
import com.example.clientadmin.ui.theme.ClientAdminTheme
import com.example.clientadmin.utils.ToastManager
import com.example.clientadmin.viewmodels.ClubViewModel
import com.example.clientadmin.viewmodels.CustomersViewModel
import com.example.clientadmin.viewmodels.HomeViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var admin: Admin? = null

        runBlocking {
            launch{
                if (intent.hasExtra("admin")) {
                    admin = Admin.fromDto(intent.getSerializableExtra("admin") as AuthResponseDto)
                }
            }
        }

        setContent{
            ClientAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    admin?.let {
                        CompositionLocalProvider(
                            LocalCustomersViewModel provides CustomersViewModel(),
                            LocalProductsViewModel provides ProductsViewModel(),
                            LocalClubViewModel provides ClubViewModel(),
                            LocalLeagueViewModel provides LeagueViewModel(),
                            LocalHomeViewModel provides HomeViewModel()
                        ) { Scaffold(admin = it) }
                    } ?: ToastManager.show("Admin not found")
                }
            }
        }
    }
}