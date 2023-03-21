package com.example.digiit.actiononelement

import androidx.compose.ui.graphics.Color
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.user.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.graphics.Bitmap

fun createTicket(type:String,
                 tag: String,
                 titre: String,
                 price: Int,
                 date: LocalDateTime,
                 colorIcon: Color,
                 colorTag: Color,
                 colorText: Color,
                 rating: Int,
                 comment: String,
                 bitmap: Bitmap,
                 user: User?
) {
    if(user != null) {
        val ticket = user.createTicket()
        ticket.type = TradeKinds.Food
        ticket.colorIcon = colorIcon
        ticket.colorTag = colorTag
        ticket.colorText = colorText
        ticket.comment = comment
        ticket.tag = tag
        ticket.price = price.toFloat()
        ticket.title = titre
        ticket.rating = rating.toFloat()
        ticket.type = TradeKinds.findByTitle(type)
        ticket.date = date
        ticket.image = bitmap
        ticket.save {error ->
            if (error != null)
                print(error)
            else
                print("Ticket successfully saved")
        }
    }
}

/*
fun editTicket(type:String,
               tag: String,
               titre: String,
               price: Int,
               dateTime: String,
               dateDate: String,
               colorIcon: Color,
               colorTag: Color,
               colorText: Color,
               rating: Int,
               comment: String,
               bitmap: Bitmap,
               ticket: ticket)
{
    val idx = listTickets.indexOf(ticket)
    listTickets[idx].titre = titre
    listTickets[idx].prix = price
    listTickets[idx].dateDate = dateDate
    listTickets[idx].dateTime = dateTime
    listTickets[idx].colorIcon = colorIcon
    listTickets[idx].comment = comment
    listTickets[idx].rating = rating
    listTickets[idx].bitmap = bitmap
    listTickets[idx].colorText = colorText
    listTickets[idx].colorTag = colorTag
    listTickets[idx].typeCommerce = getTagByName(type)
    listTickets[idx].tag = tag
}
*/
