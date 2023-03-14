package com.example.digiit.data.ticket

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.digiit.data.TradeKinds
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset


class RemoteTicket(private val document: DocumentReference) : LocalTicket(null) {
    fun load(item: DocumentSnapshot) {
        file = File("remote/${item.id}/user.dat")
        lastEdit = item.getTimestamp("lastEdit")!!.seconds
        type = TradeKinds.valueOf(item.getString("type")!!)
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

    override fun reload(callback: ActionCallback) {
        document.get().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                load(task.result)
                callback(null)
            } else {
                callback(task.exception)
            }
        }
    }

    override fun save(callback: ActionCallback) {
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
        document.set(data).addOnCompleteListener { task ->
            callback(task.exception)
        }
    }

    override fun delete(callback: ActionCallback) {
        document.delete().addOnCompleteListener { task ->
            callback(task.exception)
        }
    }
}