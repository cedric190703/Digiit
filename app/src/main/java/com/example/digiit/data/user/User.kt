package com.example.digiit.data.user

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ActionCallback


abstract class User {
    protected val tickets = ArrayList<Ticket>()
    protected val wallets = ArrayList<Wallet>()

    abstract var name: String
    abstract var lastname: String
    abstract var email: String

    abstract val local: Boolean

    abstract var profilePicture: Bitmap?

    fun getImageBitmapOrDefault(): ImageBitmap {
        if (profilePicture == null) {
            return ImageBitmap(1, 1)
        }
        return profilePicture!!.asImageBitmap();
    }

    abstract fun save(callback: ActionCallback)

    abstract fun queryTickets(callback: ActionCallback)

    fun getTickets(): List<Ticket> {
        return tickets
    }

    abstract fun createTicket(): Ticket

    abstract fun queryWallets(callback: ActionCallback)

    fun getWallets(): List<Wallet> {
        return wallets
    }

    abstract fun createWallet(): Wallet

    abstract fun loadProfilePicture(callback: ActionCallback)
    abstract fun saveProfilePicture(callback: ActionCallback)

    abstract fun logout(callback: ActionCallback)
}
