package com.example.digiit.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)

object NavItems {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        BottomNavItem(
            label = "Wallet",
            icon = Icons.Filled.Email,
            route = "wallet"
        ),
        BottomNavItem(
            label = "Data",
            icon = Icons.Filled.Person,
            route = "data"
        ),
        BottomNavItem(
            label = "Menu",
            icon = Icons.Filled.Menu,
            route = "menu"
        )
    )
}