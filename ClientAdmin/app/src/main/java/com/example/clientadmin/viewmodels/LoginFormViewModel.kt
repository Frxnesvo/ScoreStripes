package com.example.clientadmin.viewmodels

import android.net.Uri
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.dto.GoogleUserDto
import com.example.clientadmin.model.enumerator.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class AdminState(
    val firstName : String? = "",
    val lastName : String? = "",
    val email : String = "",

    val username : String = "",
    val birthdate : LocalDate = LocalDate.now(),
    val gender : Gender = Gender.entries[0],
    val profilePic : Uri = Uri.EMPTY,

    //error
    val isUsernameError: Boolean = !Admin.validateUsername(username),
    val isFirstNameError: Boolean = !firstName?.let { Admin.validateFirstName(it) }!!,
    val isLastNameError: Boolean = !lastName?.let { Admin.validateLastName(it) }!!,
    val isEmailError: Boolean = !Admin.validateEmail(email),
    val isBirthdateError: Boolean = !Admin.validateBirthdate(birthdate)
)

class LoginFormViewModel {
    private val _adminState = MutableStateFlow(AdminState())
    val adminState: StateFlow<AdminState> = _adminState.asStateFlow()

    fun setUserGoogleData(googleUserDto : GoogleUserDto){
        _adminState.value = _adminState.value.copy(
            firstName = googleUserDto.firstName,
            lastName = googleUserDto.lastName,
            email = googleUserDto.email
        )
    }

    fun updateProfilePic(uri: Uri){
        _adminState.value = _adminState.value.copy(profilePic = uri)
    }

    fun updateUsername(username: String){
        _adminState.value = _adminState.value.copy(username = username)
    }

    fun updateBirthdate(birthdate: LocalDate){
        _adminState.value = _adminState.value.copy(birthdate = birthdate)
    }

    fun updateGender(gender: Gender){
        _adminState.value = _adminState.value.copy(gender = gender)
    }
}