package com.example.digiit.data.user

import android.graphics.Bitmap
import com.example.digiit.data.ticket.RemoteTicket
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.RemoteWallet
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ActionCallback
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID


class RemoteUser(app: FirebaseApp, private val user: FirebaseUser) : User() {
    private val firestore = Firebase.firestore(app)
    private var logged = false

    var email = ""

    override var name = ""
    override var lastname = ""

    override val local = false

    override var profilePicture: Bitmap? = null

    fun load() {
        name = user.displayName.orEmpty()
        email = user.email.orEmpty()
    }

    override fun queryTickets(callback: ActionCallback) {
        val query = firestore.collection("users").document(user.uid).collection("tickets")
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
        val collection = firestore.collection("users").document(user.uid).collection("tickets")
        val ticket = RemoteTicket(collection.document(UUID.randomUUID().toString()))
        tickets.add(ticket)
        return ticket
    }

    override fun queryWallets(callback: ActionCallback) {
        val query = firestore.collection("users").document(user.uid).collection("wallets")
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

    override fun createWallet(): Wallet {
        val collection = firestore.collection("users").document(user.uid).collection("wallets")
        val wallet = RemoteWallet(collection.document(UUID.randomUUID().toString()))
        wallets.add(wallet)
        return wallet
    }

    override fun loadProfilePicture(callback: ActionCallback) {
        TODO("Not yet implemented")
    }

    override fun saveProfilePicture(callback: ActionCallback) {
        TODO("Not yet implemented")
    }
}