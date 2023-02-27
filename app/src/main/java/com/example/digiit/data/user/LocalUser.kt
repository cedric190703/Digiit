package com.example.digiit.data.user

import android.content.Context
import com.example.digiit.data.ticket.LocalTicket
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.utils.BinaryInputStream
import java.io.File


class LocalUser(private val ctx: Context, private val userId: String): User() {
    private var userDataFolder: File = ctx.filesDir.resolve("locals").resolve(userId)

    private var name = ""
    private var email = ""

    init {
        load()
    }

    private fun load() {
        val stream = BinaryInputStream(userDataFolder.resolve("info").inputStream())
        name = stream.readString()
        email = stream.readString()
        stream.close()
    }

    override fun isLocal(): Boolean {
        return true
    }

    override fun getName(): String {
        return name
    }

    override fun getEmail(): String {
        return email
    }

    override fun queryTickets(callback: (success: Boolean) -> Unit) {
        val folder = userDataFolder.resolve("tickets")
        for (file in folder.listFiles()!!) {
            if (file.isFile) {
                val ticket = LocalTicket(file)
                ticket.load()
                tickets.add(ticket)
            }
        }
        callback(true)
    }

    override fun addTicket(ticket: Ticket) {
        TODO("Not yet implemented")
    }
}