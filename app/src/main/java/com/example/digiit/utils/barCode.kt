package com.example.digiit.utils

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

@Composable
fun BarcodeImage(number: String) {
    val bitmap = generateBarcode(number)
    Box(modifier = Modifier.fillMaxSize().padding(start = 45.dp)) {
        Canvas(modifier = Modifier.align(Alignment.CenterStart)) {
            drawImage(bitmap.asImageBitmap())
        }
    }
}

private fun generateBarcode(number: String): Bitmap {
    val writer = MultiFormatWriter()
    try {
        val bitMatrix: BitMatrix = writer.encode(number, BarcodeFormat.CODE_128, 600, 300)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (bitMatrix.get(x, y)) Color.Black.toArgb() else Color.White.toArgb()
            }
        }
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            setPixels(pixels, 0, width, 0, 0, width, height)
        }
    } catch (e: WriterException) {
        // Handle exception here
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}
