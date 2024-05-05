package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class LeagueState(
    val nameLeague: String = "",
    val imageLeague: Bitmap? = null,
    //error
    val isNameLeagueError: Boolean = !League.validateNameLeague(nameLeague)
)
class LeagueFormViewModel{
    private val _leagueState = MutableStateFlow(LeagueState())
    val leagueState: StateFlow<LeagueState> = _leagueState.asStateFlow()

    fun updateTeam(nameLeague: String) {
        val hasError = !League.validateNameLeague(nameLeague)
        _leagueState.value = _leagueState.value.copy(
            nameLeague = nameLeague,
            isNameLeagueError = hasError
        )
    }
    fun updateImage(img: Bitmap?) {
        _leagueState.value = _leagueState.value.copy(
            imageLeague = img
        )
    }
}