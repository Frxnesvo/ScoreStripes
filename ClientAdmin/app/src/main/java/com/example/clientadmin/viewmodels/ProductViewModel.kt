package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientadmin.model.FilterBuilder
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.ProductSummary
import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductSummaryDto
import com.example.clientadmin.model.dto.ProductUpdateRequestDto
import com.example.clientadmin.utils.ConverterBitmap
import com.example.clientadmin.utils.RetrofitHandler
import com.example.clientadmin.model.dto.PageResponseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class ProductViewModel: ViewModel() {
    private var filter: Map<String, String?> = FilterBuilder().build()
    private var page = 0

    private val _productSummaries = MutableStateFlow<List<ProductSummary>>(emptyList())
    val productSummaries: StateFlow<List<ProductSummary>> = _productSummaries

    private val _addError =  mutableStateOf("")
    val addError = _addError

    private val sizePage = 2

    init { loadMoreProductSummaries() }

    fun incrementPage() {
        page += 1
        loadMoreProductSummaries()
    }

    fun setFilter(filter: Map<String, String?>) {
        this.filter = filter
        page = 0
        _productSummaries.value = emptyList()
        loadMoreProductSummaries()
    }

    private fun loadMoreProductSummaries() {
        CoroutineScope(Dispatchers.IO).launch {
            getProductSummaries(page).collect { summaries ->
                val newSummaries = summaries.content.map { dto ->  ProductSummary.fromDto(dto) }
                _productSummaries.value += newSummaries
            }
        }
    }

    private fun getProductSummaries(page: Int): Flow<PageResponseDto<ProductSummaryDto>> = flow {
        try {
            val response = RetrofitHandler.productApi.getProductsSummary(
                page,
                sizePage,
                filter
            ).awaitResponse()

            if (response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error fetching product summaries: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching product summaries: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getProduct(id: String): Flow<Product> = flow {
        try {
            val response = RetrofitHandler.productApi.getProductById(id).awaitResponse()
            if (response.isSuccessful) response.body()?.let { emit(Product.fromDto(it)) }
            else println("Error fetching product details: ${response.message()}")
        } catch (e: Exception) {
            println("Exception fetching product details: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun addProduct(productCreateRequestDto: ProductCreateRequestDto, pic1: Bitmap, pic2: Bitmap): Boolean { //TODO potrei evitare l'utilizzo del dto e passare o i campi singolarmente o il productState
        var returnValue = false
        try {
            Product(
                name = productCreateRequestDto.name,
                description = productCreateRequestDto.description,
                price = productCreateRequestDto.price,
                brand = productCreateRequestDto.brand,
                gender = productCreateRequestDto.gender,
                productCategory = productCreateRequestDto.productCategory,
                pic1 = pic1,
                pic2 = pic2,
                club = productCreateRequestDto.club,
                variants = productCreateRequestDto.variants
            )

            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.createProduct(
                    name = MultipartBody.Part.createFormData("name", productCreateRequestDto.name) ,
                    description = MultipartBody.Part.createFormData("description", productCreateRequestDto.description) ,
                    price = MultipartBody.Part.createFormData("price", productCreateRequestDto.price.toString()),
                    brand = MultipartBody.Part.createFormData("brand", productCreateRequestDto.brand) ,
                    gender = MultipartBody.Part.createFormData("gender", productCreateRequestDto.gender.name),
                    category = MultipartBody.Part.createFormData("category", productCreateRequestDto.productCategory.name),
                    picPrincipal = ConverterBitmap.convert(bitmap = pic1, fieldName = "picPrincipal"),
                    pic2 = ConverterBitmap.convert(bitmap = pic2, fieldName = "pic2"),
                    club = MultipartBody.Part.createFormData("clubName", productCreateRequestDto.club),
                    variants = productCreateRequestDto.variants.map { (key, value) ->
                        MultipartBody.Part.createFormData("variantStocks[${key.name}]", value.toString())
                    }.toTypedArray()

                ).awaitResponse()
                if (!response.isSuccessful) println("Error creating product: ${response.message()}")
                else returnValue = true
            }
            return returnValue
        } catch (e: IllegalArgumentException) {
            _addError.value = e.message ?: "Unknown error"
            return returnValue
        }
    }

    fun updateProduct(id: String, productUpdateRequestDto: ProductUpdateRequestDto, pic1: Bitmap, pic2: Bitmap): Boolean {
        var returnValue = false
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.productApi.updateProduct(
                    id = id,
                    description = productUpdateRequestDto.description,
                    price = productUpdateRequestDto.price,
                    picPrincipal = ConverterBitmap.convert(bitmap = pic1, fieldName = "picPrincipal"),
                    pic2 = ConverterBitmap.convert(bitmap = pic2, fieldName = "pic2"),
                    variants = productUpdateRequestDto.variants
                ).awaitResponse()
                if (!response.isSuccessful) println("Error creating product: ${response.message()}")
                else returnValue = true
            }
            return returnValue
        } catch (e: Exception) {
            _addError.value = e.message ?: "Unknown error"
            return returnValue
        }
    }

}
