package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class LeagueState(
    val name: String = "",
    val imageLeague: Bitmap? = null,
    val clubs: List<Club> = listOf(),
    //error
    val isNameLeagueError: Boolean = !League.validateName(name)
)
class LeagueFormViewModel{
    private val _leagueState = MutableStateFlow(LeagueState())
    val leagueState: StateFlow<LeagueState> = _leagueState.asStateFlow()

    fun updateTeam(name: String) {
        val hasError = !League.validateName(name)
        _leagueState.value = _leagueState.value.copy(
            name = name,
            isNameLeagueError = hasError
        )
    }
    fun updateImage(img: Bitmap?) {
        _leagueState.value = _leagueState.value.copy(
            imageLeague = img
        )
    }
}