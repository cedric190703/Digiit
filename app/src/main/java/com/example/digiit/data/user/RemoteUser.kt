package com.example.digiit.data.user

import com.example.digiit.data.ticket.RemoteTicket
import com.example.digiit.data.ticket.Ticket
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RemoteUser(app: FirebaseApp, private val user: FirebaseUser) : User() {
    private val firestore = Firebase.firestore(app)

    override fun isLocal(): Boolean {
        return false
    }

    override fun getName() = user.displayName.orEmpty()

    override fun getEmail() = user.email.orEmpty()

    override fun queryTickets(callback: (success: Boolean) -> Unit) {
        val query = firestore.collection("users").document(user.uid).collection("tickets")
        query.get().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                for (item in task.result.documents) {
                    val ticket = RemoteTicket(item.reference)
                    ticket.load(item)
                    tickets.add(ticket)
                }
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    override fun addTicket(ticket: Ticket) {
        TODO("Not yet implemented")
    }
}