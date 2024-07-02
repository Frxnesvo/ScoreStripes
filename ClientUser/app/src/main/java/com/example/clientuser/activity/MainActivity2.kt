package com.example.clientuser.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.clientuser.LocalCartViewModel
import com.example.clientuser.LocalClubViewModel
import com.example.clientuser.LocalCustomer
import com.example.clientuser.LocalCustomerViewModel
import com.example.clientuser.LocalLeagueViewModel
import com.example.clientuser.LocalProductViewModel
import com.example.clientuser.LocalProductsViewModel
import com.example.clientuser.LocalWishListViewModel
import com.example.clientuser.R
import com.example.clientuser.model.Customer
import com.example.clientuser.model.CustomerSerializable
import com.example.clientuser.ui.theme.ClientUserTheme
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.ClubViewModel
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.LeagueViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.ProductsViewModel
import com.example.clientuser.viewmodel.WishListViewModel

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customer = Customer.fromSerializable(
            intent.getSerializableExtra("customer") as CustomerSerializable?
        )

        setContent {
            ClientUserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    CompositionLocalProvider(
                        LocalClubViewModel provides ClubViewModel(),
                        LocalLeagueViewModel provides LeagueViewModel(),
                        LocalProductViewModel provides ProductViewModel(),
                        LocalProductsViewModel provides ProductsViewModel(),
                        LocalWishListViewModel provides WishListViewModel(),
                        LocalCartViewModel provides CartViewModel(),
                        LocalCustomer provides customer
                    ) {
                        LocalCustomer.current?.let {
                            CompositionLocalProvider(
                                LocalCustomerViewModel provides CustomerViewModel(it.id)
                            ) { Scaffold() }
                        } ?:
                        Scaffold()
                    }
                }
            }
        }
    }
}