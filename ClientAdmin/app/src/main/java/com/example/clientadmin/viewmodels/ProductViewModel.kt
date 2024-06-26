package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.utils.ToastManager
import com.example.clientadmin.viewmodels.formViewModel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class ProductViewModel: ViewModel(){
    private val _product = MutableStateFlow(ProductState())
    val product = _product.asStateFlow()

    fun getProduct(id: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.getProductById(id).awaitResponse()
                if (response.isSuccessful) response.body()?.let {
                    val product = Product.fromDto(it)
                    _product.value = _product.value.copy(
                        name = product.name,
                        club = product.club,
                        gender = product.gender,
                        pic1 = product.pic1,
                        pic2 = product.pic2,
                        brand = product.brand,
                        productCategory = product.productCategory,
                        description = product.description,
                        price = product.price,
                        variants = product.variants
                    )
                }
                else println("Error fetching product details: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception fetching product details: ${e.message}")
        }
    }

    fun addProduct(
        name:String,
        description: String,
        price: Double,
        brand: String,
        gender: Gender,
        category: ProductCategory,
        pic1: Bitmap,
        pic2: Bitmap,
        club: String,
        variants: Map<Size, Int>
    ): Boolean {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.createProduct(
                    name = MultipartBody.Part.createFormData("name", name) ,
                    description = MultipartBody.Part.createFormData("description", description) ,
                    price = MultipartBody.Part.createFormData("price", price.toString()),
                    brand = MultipartBody.Part.createFormData("brand", brand) ,
                    gender = MultipartBody.Part.createFormData("gender", gender.name),
                    category = MultipartBody.Part.createFormData("category", category.name),
                    picPrincipal = ConverterBitmap.convert(bitmap = pic1, fieldName = "picPrincipal"),
                    pic2 = ConverterBitmap.convert(bitmap = pic2, fieldName = "pic2"),
                    club = MultipartBody.Part.createFormData("clubName", club),
                    variants = variants.map { (key, value) ->
                        MultipartBody.Part.createFormData("variantStocks[${key.name}]", value.toString())
                    }.toTypedArray()
                ).awaitResponse()

                if (!response.isSuccessful) ToastManager.show("Error creating product")
                else ToastManager.show("Product created successfully")
            }
            true
        } catch (e: Exception) { false }
    }

    fun updateProduct(
        id: String,
        description: String,
        price: Double,
        pic1: Bitmap,
        pic2: Bitmap,
        variants: Map<Size, Int>
    ): Boolean {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.updateProduct(
                    id = id,
                    description = MultipartBody.Part.createFormData("description", description) ,
                    price = MultipartBody.Part.createFormData("price", price.toString()),
                    picPrincipal = ConverterBitmap.convert(bitmap = pic1, fieldName = "picPrincipal"),
                    pic2 = ConverterBitmap.convert(bitmap = pic2, fieldName = "pic2"),
                    variants = variants.map { (key, value) ->
                        MultipartBody.Part.createFormData("variantStocks[${key.name}]", value.toString())
                    }.toTypedArray()
                ).awaitResponse()
                if (!response.isSuccessful) ToastManager.show("Error updating product")
                else ToastManager.show("Product updated successfully")
            }
            true
        } catch (e: Exception) { false }
    }
}