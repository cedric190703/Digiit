package com.example.digiit.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Tag(t: String) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(40.dp))
    )
    {
        Text(
            text = "#$t",
            style = MaterialTheme.typography.h6,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}