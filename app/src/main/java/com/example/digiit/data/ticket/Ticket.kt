package com.example.digiit.data.ticket

import androidx.compose.ui.graphics.Color
import com.example.digiit.data.Tags
import java.time.LocalDateTime


abstract class Ticket(var type: Tags = Tags.Other,
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

    abstract fun reload()

    abstract fun save()

    abstract fun delete()
}

