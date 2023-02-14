package com.example.digiit

import androidx.compose.ui.graphics.Color
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
              comment: String) {
    val typeTags: tags
    typeTags = when(type){
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
    val ticket = ticket(typeTags,
        tag, titre, prix, dateTime, dateDate,
        colorIcon, colorTag, colorText, rating, comment)
    listTickets.add(ticket)
}