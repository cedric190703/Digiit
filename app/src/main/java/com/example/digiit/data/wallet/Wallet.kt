package com.example.digiit.data.wallet

import androidx.compose.ui.graphics.Color
import com.example.digiit.data.CommercialType
import com.example.digiit.data.TradeKinds
import java.time.LocalDateTime

abstract class Wallet(var type: TradeKinds = TradeKinds.Other,
                      var tag: String = "",
                      var title: String = "",
                      var price: Float = 0f,
                      var date: LocalDateTime = LocalDateTime.now(),
                      var rating: Float = 1f,
                      var comment: String = "",
                      var colorIcon: Color,
                      var colorTag: Color,
                      var colorText: Color,
                      var expiryDate: LocalDateTime = LocalDateTime.now(),
                      var walletType: CommercialType
) {
    // TODO
}