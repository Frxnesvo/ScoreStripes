package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Customer
import com.example.clientadmin.model.dto.AddressDto
import com.example.clientadmin.model.dto.CustomerProfileDto
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.service.impl.CustomerApiServiceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class CustomerViewModel(): ViewModel() {//application: Application
    //private val _application = application
    private val _list : Flow<List<Customer>> = flowOf() //TODO allUsers
    val users = _list

    private val customerApiService = CustomerApiServiceImpl()

    fun getCustomerDetails(id: String): Flow<CustomerProfileDto> = flow {
        customerApiService.getCustomerDetails(id)?.let { emit(it) }
    }

    fun getCustomerAddresses(id: String): Flow<List<AddressDto>> = flow {
        emit(customerApiService.getCustomerAddresses(id))
    }

    fun getCustomerOrders(id: String): Flow<List<OrderDto>> = flow {
        emit(customerApiService.getCustomerOrders(id))
    }

}