package com.example.digiit.data.ticket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.digiit.data.TradeKinds
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID


class RemoteTicket(private val document: DocumentReference) : LocalTicket(null) {
    private val storage = Firebase.storage(document.firestore.app)
    private var imageId = ""

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
        imageId = item.getString("image").orEmpty()
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
            "image" to imageId,
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

    override fun loadImage(callback: ActionCallback) {
        if (imageId.isNotEmpty()) {
            val ref = storage.getReference("images/$imageId")
            ref.getBytes(MAX_IMAGE_SIZE).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    image = BitmapFactory.decodeByteArray(task.result, 0, task.result.size)
                    callback(null)
                } else {
                    callback(task.exception)
                }
            }
        }
    }

    override fun saveImage(callback: ActionCallback) {
        if (image != null) {
            val bytes = ByteArrayOutputStream()
            image!!.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
            if (imageId.isEmpty()) {
                // If there is no already saved image then create a new id
                imageId = UUID.randomUUID().toString();
            }
            val ref = storage.getReference("images/$imageId")
            ref.putBytes(bytes.toByteArray()).addOnCompleteListener { task ->
                callback(task.exception)
            }
        }
    }

    companion object {
        private const val MAX_IMAGE_SIZE: Long = 1024 * 1024 * 8
    }
}