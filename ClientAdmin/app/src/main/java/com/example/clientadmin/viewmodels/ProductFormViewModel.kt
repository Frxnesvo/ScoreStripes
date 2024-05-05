package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.Quantity
import com.example.clientadmin.model.Season
import com.example.clientadmin.model.Enum.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProductState(
    val team: String = "",
    val league: String = "",
    val season: Season? = null,
    val type: Type = Type.entries[0],
    val category: Category = Category.entries[0],
    val description: String = "",
    val image1: Bitmap? = null,
    val image2: Bitmap? = null,
    val price: Double = 0.0,
    var preferred: Boolean = false,
    val quantities: Quantity = Quantity(),
    //error
    val isTeamError: Boolean = !Product.validateNameTeam(team),
    val isLeagueError: Boolean = !Product.validateNameTeam(league),
    val isSeasonError: Boolean = !Product.validateSeason(season),
    val isPriceError: Boolean = !Product.validatePrice(price),
    val isDescriptionError: Boolean = !Product.validateDescription(description),
)
class ProductFormViewModel(product: Product? = null) {
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState.asStateFlow()

    init {
        if (product != null) {
            updateTeam(product.team)
            updateLeague(product.league)
            updateSeason(product.season)
            updateType(product.type)
            updateCategory(product.category)
            updateDescription(product.description)
            updateImage1(product.image1)
            updateImage2(product.image2)
            updatePrice(product.price)
            updatePreferred(product.preferred)
            updateQuantities(product.quantities)
        }
    }

    fun updateTeam(team: String) {
        val hasError = !Product.validateNameTeam(team)
        _productState.value = _productState.value.copy(
            team = team,
            isTeamError = hasError
        )
    }
    fun updateLeague(league: String) {
        val hasError = !Product.validateLeague(league)
        _productState.value = _productState.value.copy(
            league = league,
            isTeamError = hasError
        )
    }
    fun updateSeason(season: Season) {
        val hasError = !Product.validateSeason(season)
        _productState.value = _productState.value.copy(
            season = season,
            isTeamError = hasError
        )
    }
    fun updateType(type: Type) {
        _productState.value = _productState.value.copy(
            type = type
        )
    }
    fun updateCategory(category: Category) {
        _productState.value = _productState.value.copy(
            category = category
        )
    }
    fun updateDescription(description: String) {
        val hasError = !Product.validateDescription(description)
        _productState.value = _productState.value.copy(
            description = description,
            isTeamError = hasError
        )
    }
    fun updateImage1(img: Bitmap?) {
        _productState.value = _productState.value.copy(
            image1 = img
        )
    }
    fun updateImage2(img: Bitmap?) {
        _productState.value = _productState.value.copy(
            image2 = img
        )
    }
    fun updatePrice(price: Double) {
        val hasError = !Product.validatePrice(price)
        _productState.value = _productState.value.copy(
            price = price,
            isTeamError = hasError
        )
    }
    fun updatePreferred(preferred: Boolean) {
        _productState.value = _productState.value.copy(
            preferred = preferred
        )
    }
    fun updateQuantities(quantities: Quantity) {
        _productState.value = _productState.value.copy(
            quantities = quantities
        )
    }
}