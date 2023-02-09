package com.example.digiit.Cards

import com.example.digiit.R

sealed class tags(
    val title: String,
    val icon: Int
) {
    object Alimentation : tags(
        title = "Alimentations",
        icon = R.drawable.resto
    )

    object Artisan : tags(
        title = "Artisan",
        icon = R.drawable.artisan
    )

    object Centre_commercial : tags(
        title = "Centre commercial",
        icon = R.drawable.centre_com
    )

    object Commerce_prox : tags(
        title = "Commerces de proximité",
        icon = R.drawable.com_prox
    )

    object Culture : tags(
        title = "Culture",
        icon = R.drawable.culture
    )

    object Divers : tags(
        title = "Divers",
        icon = R.drawable.divers
    )

    object Habillement : tags(
        title = "Habillement",
        icon = R.drawable.habillement
    )

    object Jardinage : tags(
        title = "Jardinage et bricolage",
        icon = R.drawable.jardinage
    )

    object Maison : tags(
        title = "Maison et décoration",
        icon = R.drawable.house
    )

    object Santé : tags(
        title = "Santé et bien-être",
        icon = R.drawable.sante
    )

    object Services : tags(
        title = "Services",
        icon = R.drawable.service
    )

    object Sports : tags(
        title = "Sports et loisirs",
        icon = R.drawable.sport
    )

    object Tourisme : tags(
        title = "Tourisme",
        icon = R.drawable.tourisme
    )

    object Transport : tags(
        title = "Transport",
        icon = R.drawable.transport
    )

    object Autre : tags(
        title = "Autre",
        icon = R.drawable.autre
    )
}