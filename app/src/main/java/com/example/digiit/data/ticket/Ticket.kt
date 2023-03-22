package com.example.digiit.data.ticket

import androidx.compose.ui.graphics.Color
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.card.Card
import java.time.LocalDateTime


abstract class Ticket(
    type: TradeKinds = TradeKinds.Other,
    tag: String = "",
    title: String = "",
    price: Float = 0f,
    date: LocalDateTime = LocalDateTime.now(),
    comment: String = "",
    colorIcon: Color = Color.Black,
    colorTag: Color = Color.Black,
    colorText: Color = Color.Black,
    var rating: Float = 0f
) : Card(type, tag, title, price, date, comment, colorIcon, colorTag, colorText)
