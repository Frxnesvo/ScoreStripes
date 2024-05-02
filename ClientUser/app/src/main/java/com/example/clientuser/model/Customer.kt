package com.example.clientuser.model

import com.example.clientuser.model.Enum.Gender
import java.time.LocalDate

class Customer(
    id: String,
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    email: String,
    birthDate: LocalDate,
    profilePicUrl: String,
    gender: Gender,
    val favouriteTeam: String,
    val addresses: List<Address>,
    val cart: Cart,
    val wishlist: WishList
): User(id, username, password, firstName, lastName, email, birthDate, profilePicUrl, gender)