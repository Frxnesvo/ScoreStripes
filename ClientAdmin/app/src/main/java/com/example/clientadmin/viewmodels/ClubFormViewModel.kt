package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.League
import com.example.clientadmin.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubState(
    val name: String = "",
    val imageClub: Bitmap? = null,
    val league: String = "",
    val products: List<Product> = listOf(),
    //error
    val isNameError: Boolean = !Club.validateName(name),
    val isLeagueError: Boolean = !League.validateName(league)
)
class ClubFormViewModel {
    private val _teamState = MutableStateFlow(ClubState())
    val teamState: StateFlow<ClubState> = _teamState.asStateFlow()

    fun updateNameClub(nameTeam: String) {
        val hasError = !Club.validateName(nameTeam)
        _teamState.value = _teamState.value.copy(
            name = nameTeam,
            isNameError = hasError
        )
    }
    fun updateImage(img: Bitmap?) {
        _teamState.value = _teamState.value.copy(
            imageClub = img
        )
    }
    fun updateLeague(nameLeague: String) {
        val hasError = !League.validateName(nameLeague)
        _teamState.value = _teamState.value.copy(
            league = nameLeague,
            isLeagueError = hasError
        )
    }
}