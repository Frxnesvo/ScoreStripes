package com.example.clientadmin.viewmodels.formViewModel

import android.graphics.Bitmap
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class LeagueState(
    val name: String = "",
    val image: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    //error
    val isNameError: Boolean = !League.validateName(name)
)
class LeagueFormViewModel{
    private val _leagueState = MutableStateFlow(LeagueState())
    val leagueState: StateFlow<LeagueState> = _leagueState.asStateFlow()

    fun updateName(name: String) {
        val hasError = !League.validateName(name)
        _leagueState.value = _leagueState.value.copy(
            name = name,
            isNameError = hasError
        )
    }
    fun updateImage(image: Bitmap) {
        _leagueState.value = _leagueState.value.copy(
            image = image
        )
    }
}