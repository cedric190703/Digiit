package com.example.digiit.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap


@Composable
fun AsyncImage(modifier: Modifier = Modifier, getter: (callback: (img: ImageBitmap) -> Unit) -> Unit) {
    val image = remember { mutableStateOf(ImageBitmap(1,1)) }
    val loaded = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = null) {
        getter { img ->
            image.value = img
            loaded.value = true
        }
    }

    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .scale(0.5f)
            .alpha(if (loaded.value) 0.0f else 1.0f)
        )
        Image(bitmap = image.value,
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(if (loaded.value) 1.0f else 0.0f)
        )
    }
}
