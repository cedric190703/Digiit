package com.example.digiit.data.ticket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.user.RemoteUser
import com.example.digiit.utils.ActionCallback
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID


class RemoteTicket(private val user: RemoteUser, private var saved: Boolean, private val document: DocumentReference) : Ticket() {
    private val storage = Firebase.storage(document.firestore.app)
    private var imageId = ""
    private var imageRef : StorageReference? = null

    fun load(item: DocumentSnapshot) {
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
        if (imageId.isNotEmpty()) {
            imageRef = storage.getReference("images/$imageId")
        }
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
            "comment" to comment,
            "image" to imageId,
            "colors" to hashMapOf(
                "icon" to colorIcon.toArgb(),
                "tag" to colorTag.toArgb(),
            ),
            "rating" to rating
        )
        document.set(data).addOnCompleteListener { task ->
            if (task.isSuccessful && !saved) {
                saved = true
                user.tickets.add(this)
            }
            callback(task.exception)
        }
    }

    override fun delete(callback: ActionCallback) {
        if (imageRef != null) {
            imageRef!!.delete().addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    deleteInFirebase(callback)
                } else {
                    callback(task1.exception)
                }
            }
        } else {
            deleteInFirebase(callback)
        }
    }

    private fun deleteInFirebase(callback: ActionCallback) {
        document.delete().addOnCompleteListener { task2 ->
            callback(task2.exception)
        }
    }

    override fun loadImage(callback: ActionCallback) {
        if (imageRef != null) {
            imageRef!!.getBytes(MAX_IMAGE_SIZE).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    image = BitmapFactory.decodeByteArray(task.result, 0, task.result.size)
                    callback(null)
                } else {
                    callback(task.exception)
                }
            }
        } else {
            callback(null);
        }
    }

    override fun saveImage(callback: ActionCallback) {
        if (image != null) {
            val bytes = ByteArrayOutputStream()
            image!!.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
            if (imageRef == null) {
                // If there is no already saved image then create a new id
                imageId = UUID.randomUUID().toString()
                imageRef = storage.getReference("images/$imageId")
            }
            imageRef!!.putBytes(bytes.toByteArray()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val data = hashMapOf(
                        "image" to imageId
                    )
                    document.set(data, SetOptions.merge()).addOnCompleteListener { task2 ->
                        callback(task2.exception)
                    }
                } else {
                    callback(task.exception)
                }
            }
        } else {
            callback(null)
        }
    }

    companion object {
        private const val MAX_IMAGE_SIZE: Long = 1024 * 1024 * 8
    }
}