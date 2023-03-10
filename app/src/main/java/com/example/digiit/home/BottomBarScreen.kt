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
        route = "TICKET",
        title = "TICKET",
        icon = Icons.Default.Home
    )

    object Wallet : BottomBarScreen(
        route = "WALLET",
        title = "WALLET",
        icon = Icons.Default.Email
    )

    object Data : BottomBarScreen(
        route = "DATA",
        title = "DATA",
        icon = Icons.Default.Share
    )

    object Menu : BottomBarScreen(
        route = "MENU",
        title = "MENU",
        icon = Icons.Default.Menu
    )
}