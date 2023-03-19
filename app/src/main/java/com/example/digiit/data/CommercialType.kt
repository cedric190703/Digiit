package com.example.digiit.data

import com.example.digiit.R

enum class CommercialType(
    val title: String,
    val icon: Int
) {
    Loyalty(
        title = "Carte de fidélité",
        icon = R.drawable.wallet
    ),
    Invoice(
        title = "Facture",
        icon = R.drawable.money
    ),
    Purchase(
        title = "Bon de commande",
        icon = R.drawable.shop
    ),
    Contract(
        title = "Contrat de vente",
        icon = R.drawable.contract
    )
}