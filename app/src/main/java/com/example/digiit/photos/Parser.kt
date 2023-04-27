package com.example.digiit.photos

import android.util.Log
import com.google.mlkit.vision.text.Text
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//Function that get text form ocr ml kit and return a list of string (title, price, date)
fun parseText(scan: Text): Triple<String, Float, LocalDateTime> {
    //Get first block of text
    val split = scan.text.split("\n")
    val title = split[0]
    // search for price : last number after "$"
    var price = 0f
    val regex = Regex("\\b(?:\\$\\d+\\.\\d{2}|\\d+\\.\\d{2}\\$)\\b")
    for (i in split.size - 1 downTo 0) {
        Log.d("OCR found", split[i])
        val res = regex.find(split[i].replace(" ", ""))
        if (res != null) {
            val p = res.value.trim('$').toFloat()
            if (p > price) {
                price = p
            }
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
    return Triple(title, price, date.atStartOfDay())
}