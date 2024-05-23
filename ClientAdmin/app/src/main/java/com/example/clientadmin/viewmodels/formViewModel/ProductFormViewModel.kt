package com.example.clientadmin.viewmodels.formViewModel

import android.net.Uri
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProductState(
    val name: String = "",
    val club: String = "",
    val gender: Gender = Gender.entries[0],
    val pic1: Uri = Uri.EMPTY,
    val pic2: Uri = Uri.EMPTY,
    val brand: String = "",
    val productCategory: ProductCategory = ProductCategory.entries[0],
    val description: String = "",
    val price: Double = 0.0,
    val variants: Map<Size, Int> = mapOf(),
    //error
    val isNameError: Boolean = !Product.validateName(name),
    val isLeagueError: Boolean = !Product.validateBrand(brand),
    val isPriceError: Boolean = !Product.validatePrice(price),
    val isBrandError: Boolean = !Product.validateBrand(brand),
    val isDescriptionError: Boolean = !Product.validateDescription(description)
)

class ProductFormViewModel(product: Product? = null) {
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState.asStateFlow()

    init {
        if (product != null) {
            updateName(product.name)
            updateClub(product.club)
            updateGender(product.gender)
            updateCategory(product.productCategory)
            updateDescription(product.description)
            updateBrand(product.brand)
            updatePic1(product.pic1)
            updatePic2(product.pic2)
            updatePrice(product.price)
            updateVariants(product.variants)
        }
    }

    fun updateName(name: String) {
        val hasError = !Product.validateName(name)
        _productState.value = _productState.value.copy(
            name = name,
            isNameError = hasError
        )
    }

    fun updateClub(club: String) {
        _productState.value = _productState.value.copy(
            club = club
        )
    }
    fun updateGender(gender: Gender) {
        _productState.value = _productState.value.copy(
            gender = gender
        )
    }
    fun updateCategory(productCategory: ProductCategory) {
        _productState.value = _productState.value.copy(
            productCategory = productCategory
        )
    }
    fun updateDescription(description: String) {
        val hasError = !Product.validateDescription(description)
        _productState.value = _productState.value.copy(
            description = description,
            isDescriptionError = hasError
        )
    }
    fun updateBrand(brand: String) {
        val hasError = !Product.validateDescription(brand)
        _productState.value = _productState.value.copy(
            description = brand,
            isBrandError = hasError
        )
    }
    fun updatePic1(pic: Uri) {
        _productState.value = _productState.value.copy(
            pic1 = pic
        )
    }
    fun updatePic2(pic: Uri) {
        _productState.value = _productState.value.copy(
            pic2 = pic
        )
    }
    fun updatePrice(price: Double) {
        val hasError = !Product.validatePrice(price)
        _productState.value = _productState.value.copy(
            price = price,
            isPriceError = hasError
        )
    }
    private fun updateVariants(variants: Map<Size, Int>) {
        _productState.value = _productState.value.copy(
            variants = variants
        )
    }
    fun updateVariant(size: Size, quantity: Int) {
        val variantsUpdate = productState.value.variants.toMutableMap()
        variantsUpdate[size] = quantity
        updateVariants(variantsUpdate)
    }
}