package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.ExampleProduct
import com.example.clientuser.model.Product

class ProductViewModel: ViewModel() {
    fun getMostSelledProducts(): List<ExampleProduct>{
        return listOf(
            ExampleProduct(),
            ExampleProduct(),
            ExampleProduct(),
            ExampleProduct(),
            ExampleProduct()
        )
    }
}