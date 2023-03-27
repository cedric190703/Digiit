package com.example.digiit.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentMonth(): String {
    val dateFormat = SimpleDateFormat("MMMM", Locale("fr", "FR"))
    val currentDate = Date()
    return dateFormat.format(currentDate)
}