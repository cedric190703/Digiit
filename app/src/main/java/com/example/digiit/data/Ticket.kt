package com.example.digiit.data

import androidx.compose.ui.graphics.Color

data class Ticket(var type: Tags,
                  var tag: String,
                  var title: String,
                  var price: Int,
                  var dateTime: String,
                  var dateDate: String,
                  var colorIcon: Color,
                  var colorTag: Color,
                  var colorText: Color,
                  var rating: Int,
                  var comment: String)
