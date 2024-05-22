package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductViewModel(): ViewModel() {
    private val _list : Flow<List<Product>> = flowOf() //TODO allProducts
    val products = _list

    fun getProduct(id: Int): Flow<Product?>{
        //TODO
        return flowOf()
    }
    fun addProduct(name: String, team: String, gender: Gender, productCategory: ProductCategory, description: String, pic1: Uri, pic2: Uri, price: Double, variant: Map<Size, Int>){
        //TODO
        println("product added")
    }
    fun updateProduct(name: String, team: String, gender: Gender, productCategory: ProductCategory, description: String, pic1: Uri, pic2: Uri, price: Double, variant: Map<Size, Int>){
        //TODO
    }
}