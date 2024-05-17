package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.enumerator.Gender
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
    val profilePic: Bitmap?,
    val favouriteClub: String,
    val addresses: List<Address>,
    val cart: Cart,
    val wishlist: WishList
): User(id, username, password, firstName, lastName, email, birthDate, profilePicUrl, gender)