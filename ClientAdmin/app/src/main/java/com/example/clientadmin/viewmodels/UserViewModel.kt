package com.example.clientadmin.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Address
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Enum.Size
import com.example.clientadmin.model.Enum.Type
import com.example.clientadmin.model.Order
import com.example.clientadmin.model.OrderProduct
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.Quantity
import com.example.clientadmin.model.Season
import com.example.clientadmin.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.Year

class UserViewModel(): ViewModel() {//application: Application
    //private val _application = application
    private val _list : Flow<List<User>> = flowOf() //TODO allUsers
    val users = _list

    fun getUser(id: Int): Flow<User?>{
        val l: MutableList<OrderProduct> = mutableListOf()

        repeat(5) {
            l.add(
                OrderProduct(
                    Product(
                        1,
                        "Arsenal",
                        "Premier",
                        Season(Year.now(), Year.now().plusYears(1)),
                        Type.MAN,
                        Category.JERSEY,
                        "jdvnkd",
                        null,
                        null,
                        109.99,
                        false,
                        Quantity()
                    ),
                    Size.M,
                    3
                )
            )
        }

        val address = Address(
            "Cosenza",
            "Montalto Uffugo",
            "Via Giacomo Puccini",
            25,
            1,
            false
        )

        val order = Order(
            1,
            2,
            l,
            LocalDate.now(),
            address,
            109.99
        )

        //TODO
        return flowOf(
            User(
                1,
                "Aldo",
                "Gioia",
                "aldo@gmail.com",
                "Giolda02@",
                listOf(order),
                listOf(address),
                null
            )
        )
    }
}