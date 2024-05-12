package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Cart
import com.example.clientadmin.model.Customer
import com.example.clientadmin.model.Enum.WishListVisibility
import com.example.clientadmin.model.WishList
import com.example.clientadmin.model2.m.model.Address
import com.example.clientadmin.model2.m.model.Enum.Category
import com.example.clientadmin.model2.m.model.Enum.Size
import com.example.clientadmin.model2.m.model.Enum.Type
import com.example.clientadmin.model2.m.model.Order
import com.example.clientadmin.model2.m.model.OrderProduct
import com.example.clientadmin.model2.m.model.Product
import com.example.clientadmin.model2.m.model.Quantity
import com.example.clientadmin.model2.m.model.Season
import com.example.clientadmin.model2.m.model.User
import com.example.clientuser.model.Enum.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.Year

class CustomerViewModel(): ViewModel() {//application: Application
    //private val _application = application
    private val _list : Flow<List<Customer>> = flowOf() //TODO allUsers
    val users = _list

    fun getCustomer(id: Int): Flow<Customer?>{
        //TODO
        return flowOf()
    }
}