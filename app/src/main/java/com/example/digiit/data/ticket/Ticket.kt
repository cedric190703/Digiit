package com.example.digiit.data.ticket

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import com.example.digiit.data.TradeKinds
import java.time.LocalDateTime


typealias ActionCallback = (error: Exception?) -> Unit


abstract class Ticket(var type: TradeKinds = TradeKinds.Other,
                      var tag: String = "",
                      var title: String = "",
                      var price: Float = 0f,
                      var date: LocalDateTime = LocalDateTime.now(),
                      var rating: Float = 1f,
                      var comment: String = "",
                      var colorIcon: Color = Color.Black,
                      var colorTag: Color = Color.Black,
                      var colorText: Color = Color.Black
) {
    var lastEdit: Long = -1

    abstract fun reload(callback: ActionCallback)

    abstract fun save(callback: ActionCallback)

    abstract fun delete(callback: ActionCallback)

    abstract fun loadImage(callback: (error: Exception?, bitmap: Bitmap?) -> Unit)
}
