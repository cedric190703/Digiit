package com.example.digiit.actiononelement

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.example.digiit.data.Tags
import com.example.digiit.home.listTickets
import com.example.digiit.home.ticket

fun createTicket(type:String,
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
                 painter: Painter
) {
    val ticket = ticket(
        getTagByName(type),
        tag, titre, price, dateTime, dateDate,
        colorIcon, colorTag, colorText, rating, comment, painter)
    listTickets.add(ticket)
}

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
               painter: Painter,
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
    listTickets[idx].painter = painter
    listTickets[idx].colorText = colorText
    listTickets[idx].colorTag = colorTag
    listTickets[idx].typeCommerce = getTagByName(type)
    listTickets[idx].tag = tag
}

fun getTagByName(type:String): Tags
{
    for (tag in Tags.values()) {
        if (tag.title.equals(type, true))
            return tag
    }
    return Tags.Other
}