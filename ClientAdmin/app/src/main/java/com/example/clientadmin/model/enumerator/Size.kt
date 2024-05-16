package com.example.clientadmin.model.enumerator

enum class Size {
    XS,
    S,
    M,
    L,
    XL;

    override fun toString(): String{
        return when(this){
            XS -> "XS"
            S -> "S"
            M -> "M"
            L -> "L"
            XL -> "XL"
        }
    }
}