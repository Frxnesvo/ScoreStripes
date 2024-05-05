package com.example.clientadmin.model

import androidx.compose.ui.graphics.painter.Painter

data class Address(
    val city: String,
    val municipality: String,
    val street: String,
    val houseNumber: Int,
    val postalNumber: Int,
    val preferred: Boolean
)
class User(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val orders: List<Order>,
    val addresses: List<Address>,
    val image: Painter?
){
    init {
        if (!validateNameOrSurname(name))
            throw IllegalArgumentException("Name cannot be empty and greater than 20 characters")
        if (!validateNameOrSurname(surname))
            throw IllegalArgumentException("Surname cannot be empty and greater than 20 characters")
        if (!validateEmail(email))
            throw IllegalArgumentException("Email does not respect the correct format")
        if (validatePassword(password))
            throw IllegalArgumentException("Password does not respect the correct format")
    }
    companion object{
        fun validateNameOrSurname(param: String): Boolean{
            return param.length in 1..20
        }

        fun validateEmail(email: String): Boolean{
            return email.matches("[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}".toRegex())
        }

        fun validatePassword(password: String): Boolean{
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@\$!%*?&])[A-Za-z\\\\d@\$!%*?&]{8,}\$\n".toRegex())
        }
    }
}