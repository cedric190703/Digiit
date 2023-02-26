package com.example.digiit.data

import com.example.digiit.R

enum class Tags(
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
    )
}

/*
val listOfTags = listOf<String>(Tags.Alimentation.title,Tags.Artisan.title,Tags.Centre_commercial.title,Tags.Commerce_prox.title,
Tags.Culture.title, Tags.Divers.title, Tags.Habillement.title, Tags.Jardinage.title, Tags.Maison.title, Tags.Santé.title,
    Tags.Services.title, Tags.Sports.title, Tags.Tourisme.title, Tags.Transport.title, Tags.Autre.title)
*/
