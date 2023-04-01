package com.example.digiit.data.user

import android.graphics.Bitmap
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.digiit.data.CommercialType
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ActionCallback
import java.time.LocalDateTime


abstract class User {
    val tickets = SnapshotStateList<Ticket>()
    val wallets = SnapshotStateList<Wallet>()

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

    abstract fun createTicket(): Ticket

    abstract fun queryWallets(callback: ActionCallback)

    abstract fun createWallet(): Wallet

    abstract fun getSpending(kind: TradeKinds?, after: LocalDateTime?, before: LocalDateTime?, callback: (error: Exception?, spending: Float) -> Unit)

    abstract fun loadProfilePicture(callback: ActionCallback)
    abstract fun saveProfilePicture(callback: ActionCallback)

    abstract fun logout(callback: ActionCallback)
}
