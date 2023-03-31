package com.example.digiit.data.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.toArgb
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.ticket.RemoteTicket
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.RemoteWallet
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ActionCallback
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID


class RemoteUser(private val app: FirebaseApp, private val user: FirebaseUser) : User() {
    private val firestore = Firebase.firestore(app)
    private val storage = Firebase.storage(app)

    private val document = firestore.collection("users").document(user.uid)

    private var logged = false

    override var email = ""
    override var name = ""
    override var lastname = ""

    override val local = false

    override var profilePicture: Bitmap? = null

    override fun save(callback: ActionCallback) {
        val data = hashMapOf(
            "lastname" to lastname,
            "picture" to profilePictureId
        )

        document.set(data).addOnCompleteListener { task ->
            callback(task.exception)
        }

        if (email != user.email)
            user.updateEmail(email)

        if (name != user.displayName) {
            user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
        }
    }

    private var profilePictureId = ""
    private var profilePictureRef: StorageReference? = null

    fun load(callback: ActionCallback) {
        name = user.displayName.orEmpty()
        email = user.email.orEmpty()
        document.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                lastname = task.result.getString("lastname").orEmpty()
                profilePictureId = task.result.getString("picture").orEmpty()
                if (profilePictureId.isNotEmpty()) {
                    profilePictureRef = storage.getReference("profiles/$profilePictureId")
                }
                callback(null)
            } else {
                callback(task.exception)
            }
        }
    }

    override fun queryTickets(callback: ActionCallback) {
        val query = document.collection("tickets")
        query.get().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                for (item in task.result.documents) {
                    val ticket = RemoteTicket(item.reference)
                    ticket.load(item)
                    tickets.add(ticket)
                }
                callback(null)
            } else {
                callback(task.exception)
            }
        }
    }

    override fun createTicket(): Ticket {
        val collection = document.collection("tickets")
        val ticket = RemoteTicket(collection.document(UUID.randomUUID().toString()))
        tickets.add(ticket)
        return ticket
    }

    override fun queryWallets(callback: ActionCallback) {
        val query = document.collection("wallets")
        query.get().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                for (item in task.result.documents) {
                    val wallet = RemoteWallet(item.reference)
                    wallet.load(item)
                    wallets.add(wallet)
                }
                callback(null)
            } else {
                callback(task.exception)
            }
        }
    }

    override fun createWallet(): Wallet {
        val collection = document.collection("wallets")
        val wallet = RemoteWallet(collection.document(UUID.randomUUID().toString()))
        wallets.add(wallet)
        return wallet
    }

    override fun getSpending(
        after: LocalDateTime?,
        before: LocalDateTime?,
        callback: (error: Exception?, spending: Float) -> Unit
    ) {
        var spending = 0f
        for (ticket in tickets) {
            if ((after == null || after <= ticket.date) && (before == null || ticket.date <= before)) {
                spending += ticket.price;
            }
        }
        callback(null, spending);
    }

    override fun getSpeedingIn(
        kind: TradeKinds,
        after: LocalDateTime?,
        before: LocalDateTime?
    ): Float {
        var result = 0f;
        for (ticket in tickets) {
            if ((ticket.type != null && ticket.type == kind) && (after == null || after <= ticket.date) && (before == null || ticket.date <= before)) {
                result += ticket.price;
            }
        }
        return result
    }

    override fun loadProfilePicture(callback: ActionCallback) {
        if (profilePictureRef != null) {
            profilePictureRef!!.getBytes(RemoteUser.MAX_IMAGE_SIZE).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    profilePicture = BitmapFactory.decodeByteArray(task.result, 0, task.result.size)
                    callback(null)
                } else {
                    callback(task.exception)
                }
            }
        }
    }

    override fun saveProfilePicture(callback: ActionCallback) {
        if (profilePicture != null) {
            val bytes = ByteArrayOutputStream()
            profilePicture!!.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
            if (profilePictureRef == null) {
                // If there is no already saved image then create a new id
                profilePictureId = UUID.randomUUID().toString();
                profilePictureRef = storage.getReference("images/$profilePictureId")
            }
            profilePictureRef!!.putBytes(bytes.toByteArray()).addOnCompleteListener { task ->
                callback(task.exception)
            }
        }
    }

    override fun logout(callback: ActionCallback) {
        Firebase.auth(app).signOut()
    }

    companion object {
        private const val MAX_IMAGE_SIZE: Long = 1024 * 1024 * 8
    }
}