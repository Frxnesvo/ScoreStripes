package com.example.clientuser.activity

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.clientuser.model.dto.ClubDto

@Composable
fun ClubItem(clubDto: ClubDto, onClick: () -> Unit){
    //todo mappare il dto con Club
    Image(
        painter = rememberAsyncImagePainter(Uri.EMPTY), //todo prendere da club
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    )
}