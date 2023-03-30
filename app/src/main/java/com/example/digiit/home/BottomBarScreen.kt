package com.example.digiit.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "Ticket",
        title = "Ticket",
        icon = Icons.Default.Home
    )

    object Wallet : BottomBarScreen(
        route = "Portefeuille",
        title = "Portefeuille",
        icon = Icons.Default.Email
    )

    object Data : BottomBarScreen(
        route = "Data",
        title = "Data",
        icon = Icons.Default.Share
    )

    object Menu : BottomBarScreen(
        route = "Menu",
        title = "Menu",
        icon = Icons.Default.Menu
    )
}