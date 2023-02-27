package com.example.digiit.data.user

import com.example.digiit.data.ticket.Ticket


abstract class User {
    protected val tickets = ArrayList<Ticket>()

    abstract fun isLocal(): Boolean
    abstract fun getName(): String
    abstract fun getEmail(): String
    abstract fun queryTickets(callback: (success: Boolean) -> Unit)

    fun getTickets(): List<Ticket> {
        return tickets
    }

    abstract fun addTicket(ticket: Ticket)
}
