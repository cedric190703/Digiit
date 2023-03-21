package com.example.digiit.data

import com.example.digiit.R

enum class TradeKinds(
    val title: String,
    val icon: Int
) {
    Food(
        title = "Alimentations",
        icon = R.drawable.resto
    ),

    Artisan(
        title = "Artisan",
        icon = R.drawable.artisan
    ),

    CommercialCenter(
        title = "Centre commercial",
        icon = R.drawable.centre_com
    ),

    ProximityCommerce(
        title = "Commerces de proximité",
        icon = R.drawable.com_prox
    ),

    Culture(
        title = "Culture",
        icon = R.drawable.culture
    ),

    Miscellaneous(
        title = "Divers",
        icon = R.drawable.divers
    ),

    Clothes(
        title = "Vêtements",
        icon = R.drawable.habillement
    ),

    Gardening(
        title = "Jardinage et bricolage",
        icon = R.drawable.jardinage
    ),

    House(
        title = "Maison et décoration",
        icon = R.drawable.house
    ),

    Health(
        title = "Santé et bien-être",
        icon = R.drawable.sante
    ),

    Services(
        title = "Services",
        icon = R.drawable.service
    ),

    Sports(
        title = "Sports et loisirs",
        icon = R.drawable.sport
    ),

    Tourism(
        title = "Tourisme",
        icon = R.drawable.tourisme
    ),

    Transport(
        title = "Transport",
        icon = R.drawable.transport
    ),

    Other(
        title = "Autre",
        icon = R.drawable.autre
    );

    companion object {
        fun findByTitle(title: String): TradeKinds {
            for (type in TradeKinds.values()) {
                if (type.title.equals(title, true))
                    return type
            }
            return TradeKinds.Other
        }
    }
}
