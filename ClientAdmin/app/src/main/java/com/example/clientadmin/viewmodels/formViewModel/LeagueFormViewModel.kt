package com.example.clientadmin.viewmodels.formViewModel

import android.net.Uri
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class LeagueState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    //error
    val isNameError: Boolean = !League.validateName(name),
    val isImageError: Boolean = !League.validateImage(image)
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
    fun updateImage(image: Uri) {
        val hasError = !League.validateImage(image)
        _leagueState.value = _leagueState.value.copy(
            image = image,
            isImageError = hasError
        )
    }
}