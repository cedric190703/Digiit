package com.example.digiit.data.user

import com.example.digiit.data.ticket.Ticket


abstract class User {
    protected val tickets = ArrayList<Ticket>()

    abstract var name: String
    abstract val local: Boolean

    abstract fun queryTickets(callback: (success: Boolean) -> Unit)

    fun getTickets(): List<Ticket> {
        return tickets
    }

    abstract fun createTicket(): Ticket
}
