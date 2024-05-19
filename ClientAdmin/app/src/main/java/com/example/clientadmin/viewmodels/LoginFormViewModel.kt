package com.example.clientadmin.viewmodels

import com.example.clientadmin.model.DTO.GoogleUserDto
import com.example.clientadmin.model.enumerator.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

data class UserState(
    val firstName : String? = "",
    val lastName : String? = "",
    val email : String = "",

    val username : String = "",
    val birthdate : LocalDate = LocalDate.now(),
    val gender : Gender = Gender.MALE,
    val profilePicUrl : String = ""
)

class LoginFormViewModel() {
    private val userState = MutableStateFlow(UserState())

    fun  setUserGoogleData(googleUserDto : GoogleUserDto){
        userState.value = userState.value.copy(
            firstName = googleUserDto.firstName,
            lastName = googleUserDto.lastName,
            email = googleUserDto.email
        )
    }
}