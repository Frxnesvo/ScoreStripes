package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductViewModel(): ViewModel() { //application: Application
    //private val _application = application
    private val _list : Flow<List<Product>> = flowOf() //TODO allProducts
    val products = _list

    fun getProduct(id: Int): Flow<Product?>{
        //TODO
        return flowOf()
    }
    fun addProduct(name: String, team: String, gender: Gender, productCategory: ProductCategory, description: String, pic1: Bitmap, pic2: Bitmap, price: Double, variant: Map<Size, Int>){
        //TODO
        println("product added")
    }
    fun updateProduct(name: String, team: String, gender: Gender, productCategory: ProductCategory, description: String, pic1: Bitmap, pic2: Bitmap, price: Double, variant: Map<Size, Int>){
        //TODO
    }
}