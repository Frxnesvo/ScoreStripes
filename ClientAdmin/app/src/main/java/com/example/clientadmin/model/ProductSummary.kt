package com.example.clientadmin.model

import android.net.Uri
import com.example.clientadmin.model.enumerator.Size

class ProductSummary(
    val id: String,
    val name: String,
    val clubName: String,
    val leagueName: String,
    val picUrl: String,
    val variants: Map<Size, Int>
) {
    private var pic = Uri.EMPTY
    init{
        TODO( "get a pic from bucket")
    }

    fun getPic(): Uri{
        return pic
    }
}