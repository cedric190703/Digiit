package com.example.digiit.data.wallet

import androidx.compose.ui.graphics.Color
import com.example.digiit.data.CommercialType
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.card.Card
import java.time.LocalDateTime


abstract class Wallet(
    type: TradeKinds = TradeKinds.Other,
    tag: String = "",
    title: String = "",
    price: Float = 0f,
    date: LocalDateTime = LocalDateTime.now(),
    comment: String = "",
    colorIcon: Color = Color.Black,
    colorTag: Color = Color.Black,
    colorText: Color = Color.Black,
    var expiryDate: LocalDateTime = LocalDateTime.now().plusDays(7),
    var walletType: CommercialType = CommercialType.Unknown,
    var used: Boolean = false
): Card(type, tag, title, price, date, comment, colorIcon, colorTag)
