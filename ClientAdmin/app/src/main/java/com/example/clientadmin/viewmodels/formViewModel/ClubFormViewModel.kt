package com.example.clientadmin.viewmodels.formViewModel

import android.net.Uri
import com.example.clientadmin.model.Club
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val league: String = "",
    //error
    val isNameError: Boolean = !Club.validateName(name)
)
class ClubFormViewModel {
    private val _clubState = MutableStateFlow(ClubState())
    val clubState: StateFlow<ClubState> = _clubState.asStateFlow()

    fun updateNameClub(nameTeam: String) {
        val hasError = !Club.validateName(nameTeam)
        _clubState.value = _clubState.value.copy(
            name = nameTeam,
            isNameError = hasError
        )
    }
    fun updateImage(image: Uri) {
        _clubState.value = _clubState.value.copy(
            image = image
        )
    }
    fun updateLeague(nameLeague: String) {
        _clubState.value = _clubState.value.copy(
            league = nameLeague
        )
    }
}