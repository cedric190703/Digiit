package com.example.digiit.data.card

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.digiit.data.TradeKinds
import com.example.digiit.utils.ActionCallback
import java.time.LocalDateTime


abstract class Card(var type: TradeKinds = TradeKinds.Other,
                    var tag: String = "",
                    var title: String = "",
                    var price: Float = 0f,
                    var date: LocalDateTime = LocalDateTime.now(),
                    var comment: String = "",
                    var colorIcon: Color = Color.Blue,
                    var colorTag: Color = Color.Red
) {
    var lastEdit: Long = -1
    var image: Bitmap? = null

    fun getImageBitmapOrDefault(): ImageBitmap {
        if (image == null) {
            return ImageBitmap(1, 1)
        }
        return image!!.asImageBitmap();
    }

    abstract fun reload(callback: ActionCallback)

    abstract fun save(callback: ActionCallback)

    abstract fun delete(callback: ActionCallback)

    abstract fun loadImage(callback: ActionCallback)

    abstract fun saveImage(callback: ActionCallback)
}