package com.example.digiit

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.example.digiit.Cards.tags
import com.example.digiit.Home.listTickets
import com.example.digiit.Home.ticket

fun addTicket(type:String,
              tag: String,
              titre: String,
              prix: Int,
              dateTime: String,
              dateDate: String,
              colorIcon: Color,
              colorTag: Color,
              colorText: Color,
              rating: Int,
              comment: String,
              painter: Painter
) {
    val typeTags: tags
    typeTags = getIcon(type)
    val ticket = ticket(typeTags,
        tag, titre, prix, dateTime, dateDate,
        colorIcon, colorTag, colorText, rating, comment, painter)
    listTickets.add(ticket)
}

fun modifTicket(type:String,
                tag: String,
                titre: String,
                prix: Int,
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
    val typeTags: tags
    typeTags = getIcon(type)
    listTickets[idx].titre = titre
    listTickets[idx].prix = prix
    listTickets[idx].dateDate = dateDate
    listTickets[idx].dateTime = dateTime
    listTickets[idx].colorIcon = colorIcon
    listTickets[idx].comment = comment
    listTickets[idx].rating = rating
    listTickets[idx].painter = painter
    listTickets[idx].colorText = colorText
    listTickets[idx].colorTag = colorTag
    listTickets[idx].typeCommerce = typeTags
    listTickets[idx].tag = tag
}

fun getIcon(type:String): tags
{
    var icon = when(type){
        tags.Artisan.title -> tags.Artisan
        tags.Alimentation.title -> tags.Alimentation
        tags.Centre_commercial.title -> tags.Centre_commercial
        tags.Commerce_prox.title -> tags.Commerce_prox
        tags.Culture.title -> tags.Culture
        tags.Divers.title -> tags.Divers
        tags.Habillement.title -> tags.Habillement
        tags.Jardinage.title -> tags.Jardinage
        tags.Maison.title -> tags.Maison
        tags.Santé.title -> tags.Santé
        tags.Services.title -> tags.Services
        tags.Sports.title -> tags.Sports
        tags.Tourisme.title -> tags.Tourisme
        tags.Transport.title -> tags.Transport
        else -> tags.Autre
    }
    return icon
}