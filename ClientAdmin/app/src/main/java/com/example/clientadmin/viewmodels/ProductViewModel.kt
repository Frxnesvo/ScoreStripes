package com.example.clientadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.ProductPic
import com.example.clientadmin.model.ProductWithVariant
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductViewModel(): ViewModel() { //application: Application
    //private val _application = application
    private val _list : Flow<List<Product>> = flowOf() //TODO allProducts
    val products = _list

    fun addProduct(name: String, team: String, league: String, gender: Gender, productCategory: ProductCategory, description: String, list: List<ProductPic>, price: Double, preferred: Boolean, variant: ProductWithVariant){
        //TODO
        println("product added")
    }
    fun getProduct(id: Int): Flow<Product?>{
        //TODO
        return flowOf()
    }

    fun updateProduct(name: String, team: String, league: String, gender: Gender, productCategory: ProductCategory, description: String, list: List<ProductPic>, price: Double, preferred: Boolean, variant: ProductWithVariant){
        //TODO
    }
}