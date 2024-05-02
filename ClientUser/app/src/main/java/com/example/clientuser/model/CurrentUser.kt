package com.example.clientuser.model


//TODO da settarlo con l'utente una volta che fa il login o la register
object CurrentUser{
    private var currentUser: User? = null

    fun setUser(user: User){
        this.currentUser = user
    }

    fun getUser(): User?{
        return this.currentUser
    }
}