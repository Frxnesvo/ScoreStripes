package com.example.clientadmin.model

import android.content.Context
import android.net.Uri
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.dto.AdminDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.service.ConverterUri
import java.time.LocalDate

class Admin(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val pic: Uri
) {
    init {
        require(validateUsername(username)) { "Invalid username: must be between 3 and 20 characters" }
        require(validateFirstName(firstName)) { "Invalid first name: must be between 3 and 20 characters" }
        require(validateLastName(lastName)) { "Invalid last name: must be between 3 and 20 characters" }
        require(validateEmail(email)) { "Invalid email format" }
        require(validateBirthdate(birthDate)) { "Invalid birthdate: must be before the current date" }
    }

    fun request(context: Context): AdminCreateRequestDto {
        try {
            val multipart = ConverterUri.convert(context, pic, "pic")
            return AdminCreateRequestDto(username, firstName, lastName, birthDate, multipart!!, gender)
        }
        catch (e: Exception) { throw e }
    }

    companion object {
        //TODO matchare i controlli di validazione con quelli del backend
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

        fun fromDto(adminDto: AdminDto): Admin{
            //TODO settare l'immagine
            return Admin(
                username = adminDto.username,
                firstName = adminDto.firstName,
                lastName = adminDto.lastName,
                email = adminDto.email,
                birthDate = adminDto.birthDate,
                gender = adminDto.gender,
                pic = Uri.EMPTY
            )
        }
    }
}