package com.example.clientadmin.viewmodels.formViewModel

import android.graphics.Bitmap
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
    val pic1: Bitmap? = null,
    val pic2: Bitmap? = null,
    val brand: String = "",
    val productCategory: ProductCategory = ProductCategory.entries[0],
    val description: String = "",
    val price: Double? = null,
    val variants: Map<Size, Int> = mapOf(
        Pair(Size.XS, 0),
        Pair(Size.S, 0),
        Pair(Size.M, 0),
        Pair(Size.L, 0),
        Pair(Size.XL, 0),
    ),

    //error
    val isNameError: Boolean = !Product.validateName(name),
    val isLeagueError: Boolean = !Product.validateBrand(brand),
    val isPriceError: Boolean = !Product.validatePrice(price),
    val isPicPrincipalError: Boolean = !Product.validatePic(pic1),
    val isPicSecondaryError: Boolean = !Product.validatePic(pic2),
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
        _productState.value = _productState.value.copy(
            name = name,
            isNameError = !Product.validateName(name)
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
        _productState.value = _productState.value.copy(
            description = description,
            isDescriptionError = !Product.validateDescription(description)
        )
    }
    fun updateBrand(brand: String) {
        _productState.value = _productState.value.copy(
            brand = brand,
            isBrandError = !Product.validateBrand(brand)
        )
    }
    fun updatePic1(pic: Bitmap?) {
        _productState.value = _productState.value.copy(
            pic1 = pic,
            isPicPrincipalError = !Product.validatePic(pic)
        )
    }
    fun updatePic2(pic: Bitmap?) {
        _productState.value = _productState.value.copy(
            pic2 = pic,
            isPicSecondaryError = !Product.validatePic(pic)
        )
    }
    fun updatePrice(price: Double?) {
        _productState.value = _productState.value.copy(
            price = price,
            isPriceError = !Product.validatePrice(price)
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