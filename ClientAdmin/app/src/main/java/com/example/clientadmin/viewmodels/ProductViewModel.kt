package com.example.clientadmin.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.Quantity
import com.example.clientadmin.model.Season
import com.example.clientadmin.model.Enum.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.Year

class ProductViewModel(): ViewModel() { //application: Application
    //private val _application = application
    private val _list : Flow<List<Product>> = flowOf() //TODO allProducts
    val products = _list

    fun addProduct(team: String, league: String, season: Season?, type: Type, category: Category, description: String, image1: Bitmap?, image2: Bitmap?, price: Double, preferred: Boolean, quantity: Quantity){
        //TODO
        println("product added")
    }
    fun getProduct(id: Int): Flow<Product?>{
        //TODO
        return flowOf(
            Product(
                1,
                "Milan",
                "Serie A",
                Season(Year.now(), Year.now().plusYears(1)),
                Type.WOMAN,
                Category.JERSEY,
                "Maglia bella",
                null,
                null,
                119.99,
                false,
                Quantity()
            )
        )
    }

    fun updateProduct(team: String, league: String, season: Season?, type: Type, category: Category, description: String, image1: Bitmap?, image2: Bitmap?, price: Double, preferred: Boolean, quantity: Quantity){
        //TODO
    }

    /*fun setPreferred(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            product.preferred = true
            //TODO
        }
    }
    fun removedPreferred(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            product.preferred = false
            //TODO
        }
    }*/
}