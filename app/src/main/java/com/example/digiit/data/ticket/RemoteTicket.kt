package com.example.digiit.data.ticket

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.digiit.data.Tags
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime
import java.time.ZoneOffset

class RemoteTicket(private val document: DocumentReference) : Ticket() {
    fun load(item: DocumentSnapshot) {
        lastEdit = item.getTimestamp("lastEdit")!!.seconds
        type = Tags.valueOf(item.getString("type")!!)
        tag = item.getString("tag")!!
        title = item.getString("title")!!
        price = item.getDouble("price")!!.toFloat()
        date = LocalDateTime.ofEpochSecond(item.getLong("date")!!, 0, ZoneOffset.UTC)
        rating = item.getDouble("rating")!!.toFloat()
        comment = item.getString("comment")!!
        colorIcon = Color(item.getLong("colors.icon")!!)
        colorTag = Color(item.getLong("colors.tag")!!)
        colorText = Color(item.getLong("colors.text")!!)
    }

    override fun reload() {
        TODO("Not yet implemented")
    }

    override fun save() {
        val data = hashMapOf(
            "lastEdit" to FieldValue.serverTimestamp(),
            "type" to type.name,
            "tag" to tag,
            "title" to title,
            "price" to price,
            "date" to date.toEpochSecond(ZoneOffset.UTC),
            "rating" to rating,
            "comment" to comment,
            "colors" to hashMapOf(
                "icon" to colorIcon.toArgb(),
                "tag" to colorTag.toArgb(),
                "text" to colorText.toArgb()
            )
        )
        document.set(data)
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}