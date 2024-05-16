package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Customer
import com.example.clientadmin.model.Enum.WishListVisibility
import com.example.clientadmin.model.WishList
import com.example.clientuser.model.Enum.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CustomerViewModel(): ViewModel() {//application: Application
    //private val _application = application
    private val _list : Flow<List<Customer>> = flowOf() //TODO allUsers
    val users = _list

    fun getCustomer(id: Int): Flow<Customer?>{
        //TODO
        return flowOf()
    }
}