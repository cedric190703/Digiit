package com.example.digiit.data.user

import android.content.Context
import android.graphics.Bitmap
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.ticket.LocalTicket
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ActionCallback
import com.example.digiit.utils.BinaryInputStream
import java.io.File
import java.time.LocalDateTime


class LocalUser(private val ctx: Context, private val userId: String): User() {
    private var userDataFolder: File = ctx.filesDir.resolve("locals").resolve(userId)

    override var email = ""
    override var name = "unimplemented"
    override var lastname = "unimplemented"

    override val local = true
    override var profilePicture: Bitmap? = null

    override fun save(callback: ActionCallback) {
        TODO("Not yet implemented")
    }

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

    override fun getSpending(
        kind: TradeKinds?,
        after: LocalDateTime?,
        before: LocalDateTime?,
        callback: (error: Exception?, spending: Float) -> Unit
    ) {
        TODO("Not yet implemented")
    }
    override fun getSpeedingIn(
        kind: TradeKinds,
        after: LocalDateTime?,
        before: LocalDateTime?
    ): Float {
        TODO("Not yet implemented")
    }

    override fun getSpendingWithRating(
        kind: TradeKinds,
        after: LocalDateTime?,
        before: LocalDateTime?
    ): Pair<Float, Float> {
        TODO("Not yet implemented")
    }

    override fun loadProfilePicture(callback: ActionCallback) {
        TODO("Not yet implemented")
    }

    override fun saveProfilePicture(callback: ActionCallback) {
        TODO("Not yet implemented")
    }

    override fun logout(callback: ActionCallback) {}
}