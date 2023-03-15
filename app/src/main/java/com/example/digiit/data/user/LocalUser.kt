package com.example.digiit.data.user

import android.content.Context
import com.example.digiit.data.ticket.LocalTicket
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.utils.BinaryInputStream
import java.io.File


class LocalUser(private val ctx: Context, private val userId: String): User() {
    private var userDataFolder: File = ctx.filesDir.resolve("locals").resolve(userId)

    private var email = ""

    override var name = "unimplemented"

    override val local = true

    init {
        load()
    }

    private fun load() {
        val stream = BinaryInputStream(userDataFolder.resolve("info").inputStream())
        name = stream.readString()
        email = stream.readString()
        stream.close()
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

    override fun createTicket(): Ticket {
        TODO("Not yet implemented")
    }
}