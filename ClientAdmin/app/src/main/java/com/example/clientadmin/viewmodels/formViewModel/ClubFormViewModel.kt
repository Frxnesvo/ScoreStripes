package com.example.clientadmin.viewmodels.formViewModel

import android.graphics.Bitmap
import com.example.clientadmin.model.Club
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubState(
    val name: String = "",
    val image: Bitmap? = null,
    val league: String = "",
    //error
    val isNameError: Boolean = !Club.validateName(name),
    val isImageError: Boolean = !Club.validateImage(image),
)
class ClubFormViewModel(club: Club? = null) {
    private val _clubState = MutableStateFlow(ClubState())
    val clubState: StateFlow<ClubState> = _clubState.asStateFlow()

    init {
        if (club != null) {
            updateNameClub(club.name)
            updateImage(club.pic)
            updateLeague(club.league)
        }
    }

    fun updateNameClub(nameTeam: String) {
        _clubState.value = _clubState.value.copy(
            name = nameTeam,
            isNameError = !Club.validateName(nameTeam)
        )
    }
    fun updateImage(image: Bitmap?) {
        _clubState.value = _clubState.value.copy(
            image = image,
            isImageError = !Club.validateImage(image)
        )
    }
    fun updateLeague(nameLeague: String) {
        _clubState.value = _clubState.value.copy(
            league = nameLeague
        )
    }
}