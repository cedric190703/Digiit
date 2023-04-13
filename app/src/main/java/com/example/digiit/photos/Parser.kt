package com.example.digiit.photos

import android.util.Log
import com.google.mlkit.vision.text.Text
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//Function that get text form ocr ml kit and return a list of string (title, price, date)
fun parseText(scan: Text): Triple<String, Float, LocalDateTime> {
    //Get first block of text
    var split = scan.text.split("\n")
    val title = split[0]
    // search for price : last number after "$"
    var price = "0"
    for (i in split.size - 1 downTo 0) {
        if (split[i].contains("$") && split[i].length > 1) {
            price = split[i].split("$")[1]
            break
        }
    }
    var sdate = ""
    for (i in split.size - 1 downTo 0) {
        // TODO optionel : add regex for other date format
        if (split[i].matches(Regex("\\d{4}/\\d{2}/\\d{2}"))) {
            sdate = split[i]
            break
        }
    }
    var date = LocalDate.now()
    try{
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        date = LocalDate.parse(sdate, formatter)
    }catch (e: Exception){
        Log.d("OCR", e.toString())
    }
    return Triple(title, price.toFloat(), date.atStartOfDay())
}