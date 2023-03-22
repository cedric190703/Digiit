package com.example.digiit.data.user

import android.content.Context
import android.graphics.Bitmap
import com.example.digiit.data.ticket.LocalTicket
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ActionCallback
import com.example.digiit.utils.BinaryInputStream
import java.io.File


class LocalUser(private val ctx: Context, private val userId: String): User() {
    private var userDataFolder: File = ctx.filesDir.resolve("locals").resolve(userId)

    private var email = ""

    override var name = "unimplemented"
    override var lastname = "unimplemented"

    override val local = true
    override var profilePicture: Bitmap? = null

    init {
        load()
    }

    private fun load() {
        val stream = BinaryInputStream(userDataFolder.resolve("info").inputStream())
        name = stream.readString()
        email = stream.readString()
        stream.close()
    }

    override fun queryTickets(callback: ActionCallback) {
        val folder = userDataFolder.resolve("tickets")
        for (file in folder.listFiles()!!) {
            if (file.isFile) {
                val ticket = LocalTicket(file)
                ticket.load()
                tickets.add(ticket)
            }
        }
        callback(null)
    }

    override fun createTicket(): Ticket {
        TODO("Not yet implemented")
    }

    override fun queryWallets(callback: ActionCallback) {
        TODO("Not yet implemented")
    }

    override fun createWallet(): Wallet {
        TODO("Not yet implemented")
    }

    override fun loadProfilePicture(callback: ActionCallback) {
        TODO("Not yet implemented")
    }

    override fun saveProfilePicture(callback: ActionCallback) {
        TODO("Not yet implemented")
    }
}