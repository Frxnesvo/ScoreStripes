package com.example.clientadmin.model

import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import com.example.clientadmin.model.enumerator.Gender
import java.time.LocalDate

class Admin(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: LocalDate,
    val gender: Gender,
    private val picUrl: String,
    val pic: Uri = Uri.EMPTY
) {
    init {
        //Prendere l'immagine dal bucket
        validateUsername(username)
        validateFirstName(firstName)
        validateLastName(lastName)
        validateEmail(email)
        validateBirthdate(birthDate)
    }

    companion object {
        fun validateUsername(username: String): Boolean {
            return username.length in 3..20
        }
        fun validateFirstName(firstName: String): Boolean {
            return firstName.length in 3..20
        }
        fun validateLastName(lastName: String): Boolean {
            return lastName.length in 3..20
        }
        fun validateEmail(email: String): Boolean {
            return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        }
        fun validateBirthdate(birthdate: LocalDate): Boolean {
            return birthdate.isBefore(LocalDate.now())
        }
    }
}