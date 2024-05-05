package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import com.example.clientadmin.model.League
import com.example.clientadmin.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TeamState(
    val nameTeam: String = "",
    val imageTeam: Bitmap? = null,
    val league: String = "",
    //error
    val isNameTeamError: Boolean = !Team.validateNameTeam(nameTeam),
    val isLeagueError: Boolean = !Team.validateNameLeague(league)
)
class TeamFormViewModel {
    private val _teamState = MutableStateFlow(TeamState())
    val teamState: StateFlow<TeamState> = _teamState.asStateFlow()

    fun updateNameTeam(nameTeam: String) {
        val hasError = !League.validateNameLeague(nameTeam)
        _teamState.value = _teamState.value.copy(
            nameTeam = nameTeam,
            isNameTeamError = hasError
        )
    }
    fun updateImage(img: Bitmap?) {
        _teamState.value = _teamState.value.copy(
            imageTeam = img
        )
    }

    fun updateLeague(nameLeague: String) {
        val hasError = !League.validateNameLeague(nameLeague)
        _teamState.value = _teamState.value.copy(
            league = nameLeague,
            isLeagueError = hasError
        )
    }
}