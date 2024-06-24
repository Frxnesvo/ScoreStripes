package com.example.clientadmin.viewmodels.formViewModel

import android.graphics.Bitmap
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class LeagueState(
    val name: String = "",
    val image: Bitmap? = null,
    //error
    val isNameError: Boolean = !League.validateName(name),
    val isImageError: Boolean = !League.validateImage(image)
)
class LeagueFormViewModel(league: League? = null){
    private val _leagueState = MutableStateFlow(LeagueState())
    val leagueState: StateFlow<LeagueState> = _leagueState.asStateFlow()

    init {
        if (league != null) {
            updateName(league.name)
            updateImage(league.pic)
        }
    }
    fun updateName(name: String) {
        _leagueState.value = _leagueState.value.copy(
            name = name,
            isNameError = !League.validateName(name)
        )
    }
    fun updateImage(image: Bitmap?) {
        _leagueState.value = _leagueState.value.copy(
            image = image,
            isImageError = !League.validateImage(image)
        )
    }
}